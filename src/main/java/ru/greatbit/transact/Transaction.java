package ru.greatbit.transact;

/**
 * Created by azee on 12.08.14.
 */
public @interface Transaction {
    public String methodName();
    public String lockIdPath();
}
