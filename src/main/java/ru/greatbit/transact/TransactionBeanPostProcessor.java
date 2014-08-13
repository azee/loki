package ru.greatbit.transact;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.greatbit.transact.data.TransactionMethodMeta;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * Created by azee on 12.08.14.
 */
public class TransactionBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> map = new HashMap<String, Class>();

    @Autowired
    LockProvider lockProvider;

    @Autowired
    KeyProvider keyProvider;

    @Autowired
    TransactionsProvider transactionsProvider;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Class<?> beanClass = o.getClass();
        if (beanClass.isAnnotationPresent(Transactionable.class)){
            map.put(s, beanClass);
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(final Object o, String s) throws BeansException {
        final Class beanClass = map.get(s);
        if (beanClass != null){
            Transaction[] transactions;
            try {
                transactions = transactionsProvider.getTransactions(beanClass);
            } catch (Exception e) {
                return o;
            }

            if (transactions.length == 0){
                return o;
            }
            final Map<String, TransactionMethodMeta> methods = transactionsProvider.getTransactionableMethodsMeta(transactions);

            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (!methods.keySet().contains(method.getName())){
                        return method.invoke(o, args);
                    }

                    Lock lock = null;
                    try {
                        lock = lockProvider.getLock(keyProvider.getKey(o, beanClass, method, args, methods.get(method.getName())));
                        if (lock != null){
                            lock.lock();
                            return method.invoke(o, args);
                        }
                    } finally {
                        if (lock != null){
                            lock.unlock();
                        }
                    }
                return null;
                }
            });
        }
        return o;
    }
}
