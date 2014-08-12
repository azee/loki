package ru.greatbit.transact;

/**
 * Created by azee on 12.08.14.
 */
public interface KeyProvider {
    public String getKey(Object obj, Class clazz);
}
