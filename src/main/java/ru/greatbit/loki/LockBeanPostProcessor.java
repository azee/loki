package ru.greatbit.loki;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.greatbit.loki.data.MethodMeta;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by azee on 12.08.14.
 */
public class LockBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> map = new HashMap<String, Class>();

    @Autowired
    LockProvider lockProvider;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Class<?> beanClass = o.getClass();
        if (beanClass.isAnnotationPresent(Lockable.class)
                || classInterfacesContainAnnotation(beanClass, Lockable.class)
                || classMethodContainAnnotation(beanClass, Lock.class)){
            map.put(s, beanClass);
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(final Object o, String s) throws BeansException {
        final Class beanClass = map.get(s);
        if (beanClass != null){

            final Map<String, MethodMeta> methods = AnnotationDataProvider.getLockMethodsMeta(beanClass);
            if (methods.size() == 0){
                return o;
            }
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    String signature = String.format("%s-%s", method.getName(),
                            Arrays.asList(method.getParameterTypes()).toString());
                    if (!methods.keySet().contains(signature)){
                        return method.invoke(o, args);
                    }

                    java.util.concurrent.locks.Lock lock = null;
                    try {
                        lock = lockProvider.getLock(KeyProvider.getKey(beanClass, method, args,
                                methods.get(signature)));
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


    private boolean classInterfacesContainAnnotation(Class clazz, Class annotation){
        for (Class interfaceClass : clazz.getInterfaces()){
            if (interfaceClass.isAnnotationPresent(annotation)){
                return true;
            }
        }
        return false;
    }

    private boolean classMethodContainAnnotation(Class clazz, Class annotation){
        for (Method method : clazz.getDeclaredMethods()){
            if (method.isAnnotationPresent(annotation)){
                return true;
            }
        }
        return false;
    }
}
