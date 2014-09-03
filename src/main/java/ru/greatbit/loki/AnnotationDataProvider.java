package ru.greatbit.loki;

import org.springframework.stereotype.Service;
import ru.greatbit.loki.data.MethodMeta;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by azee on 13.08.14.
 */
@Service
public class AnnotationDataProvider {
    public static Lock[] getTransactions(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Annotation transactionable = clazz.getAnnotation(Lockable.class);
        if (transactionable == null){
            transactionable = getAnnotationFromInterfaces(clazz, Lockable.class);
        }
        if (transactionable == null) {
            return new Lock[]{};
        }
        Method method = Lockable.class.getMethod("transactions", (Class[]) null);
        return (Lock[]) method.invoke(transactionable, (Object[])null);
    }

    public static Map<String, MethodMeta> getTransactionableMethodsMeta(Lock[] locks){
        Map<String, MethodMeta> methods = new HashMap<String, MethodMeta>();
        for (Lock lock : locks){
            MethodMeta methodMeta = new MethodMeta();
            methodMeta.setName(lock.methodName());
            methodMeta.setIdArgumentIndex(lock.parameter());
            methodMeta.setIdPath(lock.lockIdPath());
            methods.put(lock.methodName(), methodMeta);
        }
        return methods;
    }

    public static Annotation getAnnotationFromInterfaces(Class clazz, Class annotation){
        for (Class interfaceClass : clazz.getInterfaces()){
            if (interfaceClass.isAnnotationPresent(annotation)){
                return interfaceClass.getAnnotation(annotation);
            }
        }
        return null;
    }
}
