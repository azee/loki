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
public class TransactionsProvider {
    public static Transaction[] getTransactions(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Annotation transactionable = clazz.getAnnotation(Transactionable.class);
        if (transactionable == null){
            transactionable = getAnnotationFromInterfaces(clazz, Transactionable.class);
        }
        if (transactionable == null) {
            return new Transaction[]{};
        }
        Method method = Transactionable.class.getMethod("transactions", (Class[]) null);
        return (Transaction[]) method.invoke(transactionable, (Object[])null);
    }

    public static Map<String, MethodMeta> getTransactionableMethodsMeta(Transaction[] transactions){
        Map<String, MethodMeta> methods = new HashMap<String, MethodMeta>();
        for (Transaction transaction : transactions){
            MethodMeta methodMeta = new MethodMeta();
            methodMeta.setName(transaction.methodName());
            methodMeta.setIdArgumentIndex(transaction.parameter());
            methodMeta.setIdPath(transaction.lockIdPath());
            methods.put(transaction.methodName(), methodMeta);
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
