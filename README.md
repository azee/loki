spring-loki
========================

Loki is a declarative locking framework created for Spring based projects.

When you are working with non-transactional datasources or shared resources it is crutial to perform opretstions consequentially.
Within a single JVM concurrent synchronization or Locks can be used. But if your system is distributive you'll need a distributive lock.

The idea is to use Locks provided by distributive shared systems such as Hazelcast or any other database.

It is recommended to use inmemory databases. Hazelcast is a good choice as it features Locks mechanism from the box.

Loki was created to avoid wrapping functions into try-catch and getting keys "by hand":
```
Lock lock = someService.getLock(GET_KEY_SOMEHOW);
try{
    lock.lock();
    doSmt();
} finally {
    lock.unlock();
}
```

How to use
========================

Key Provider
========================
First of all you'll need to implement a LockProvider interface. The only thing it does is returning a lock object (java.util.concurrent.locks.Lock implementation) by key.
E.g. if you use Hazelcast, you'll need just to return hazelcastInstance.getLock(key).

Declare Locks
========================
Second you'll have to declare what methods will be locked and how.
There are to ways to do it - by classes or by interfaces.

By classes.

This method is used if you have an access (can edit) to the implementation of the service.
- Add a @Lock annotation to the method.
- Add @LockId annotation to the parameter from which lock id will be retrieved. Can be blank. In that case a lockId will be a full qualified method name.
- Add a point delimited path (inside @LockId) to the field containing id you want to be used as a locking key. Can be blank. In that case a parameter.toString will be used as a key.

```
@Lock
public void doSmt(SomeObject objValue, @LockId(path = "idContainer.id") SomeObject id) {}
```

By interfaces.

This method is used if you do not have an access to the implementation of the service but you can extend an interface. E.g. - Spring Data PagingAndSortingRepository.
- Add a @Lockable annotation to the interface class.
- Declare @Lock - array of methods and locking configurations
- In @Lock you have to declare: methodName (method to be executed under the lock), signature - a list of types of method's arguments, parameter - a number of the parameter used to retrieve lock id (starts from 0), lockIdPath - a path to the field containing id you want to be used as a locking key.

If the method doesn't take parameters "methodName" is the only field you have to declare.
If lockIdPath is blank then parameter.toString() will be used as a lock key.

```
@Lockable(locks = {
        @Lock(methodName = "doSmt", signature = {String.class, String.class}, parameter = 1, lockIdPath = ""),
        @Lock(methodName = "doSmt1", signature = {FakeObject.class, String.class}, parameter = 1, lockIdPath = ""),
        @Lock(methodName = "doSmtWrong", signature = {String.class, String.class}, parameter = 3, lockIdPath = ""),
        @Lock(methodName = "doSmtWrong1", signature = {String.class, String.class}),
        @Lock(methodName = "doSmtNoParams"),
        @Lock(methodName = "doSmtParent", signature = {FakeObject.class, FakeObject.class}, parameter = 1, lockIdPath = "id")
})
```

Configure Spring
========================
At the end you'll have to configure a spring context as follows:

```
<bean class="ru.greatbit.loki.mock.TestLockProvider" id="lockProvider" />
<bean class="ru.greatbit.loki.LockBeanPostProcessor"/>
```

Here you declare your implementation of lockProvider and enable ru.greatbit.loki.LockBeanPostProcessor as a Spring post-processor. It will add Locking functionality to your methods declared with locking annotations.

Examples
========================
For more examples please look at the tests within the project.

Future versions
========================
In Java 8 method parameter names will be available in runtime even if the code was compiled without -d attribute. So parameterNumber will be replaced with parameterName.