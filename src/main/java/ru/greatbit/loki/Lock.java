package ru.greatbit.loki;

/**
 * Created by azee on 12.08.14.
 */
public @interface Lock {
    public String methodName();
    public int parameter();
    public String lockIdPath();
}
