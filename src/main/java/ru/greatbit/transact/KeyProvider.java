package ru.greatbit.transact;

import ru.greatbit.transact.data.TransactionMethodMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by azee on 12.08.14.
 */
public interface KeyProvider {
    public String getKey(Object obj, Class clazz, Method method, Object[] args, TransactionMethodMeta meta) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
