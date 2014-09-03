package ru.greatbit.loki;

/**
 * Created by azee on 12.08.14.
 */
public @interface Transaction {
    public String methodName();
    public int parameter();
    public String lockIdPath();
}
