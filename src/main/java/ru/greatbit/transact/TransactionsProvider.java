package ru.greatbit.transact;

import org.springframework.stereotype.Service;
import ru.greatbit.transact.data.TransactionMethodMeta;

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
    public Transaction[] getTransactions(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Annotation transactionable = clazz.getAnnotation(Transactionable.class);
        Method method = Transactionable.class.getMethod("transactions", (Class[]) null);
        return (Transaction[]) method.invoke(transactionable, (Object[])null);
    }

    public Map<String, TransactionMethodMeta> getTransactionableMethodsMeta(Transaction[] transactions){
        Map<String, TransactionMethodMeta> methods = new HashMap<String, TransactionMethodMeta>();
        for (Transaction transaction : transactions){
            TransactionMethodMeta transactionMethodMeta = new TransactionMethodMeta();
            transactionMethodMeta.setName(transaction.methodName());
            transactionMethodMeta.setIdArgumentIndex(transaction.parameter());
            transactionMethodMeta.setIdPath(transaction.lockIdPath());
            methods.put(transaction.methodName(), transactionMethodMeta);
        }
        return methods;
    }
}
