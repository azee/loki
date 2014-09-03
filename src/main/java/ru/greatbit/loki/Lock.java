package ru.greatbit.loki;

/**
 * Created by azee on 12.08.14.
 */
public @interface Lock {
    public String methodName() default "";
    public int parameter() default -1;
    public String lockIdPath() default "";
}
