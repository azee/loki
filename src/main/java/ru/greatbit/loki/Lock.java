package ru.greatbit.loki;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by azee on 12.08.14.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {
    public String methodName() default "";
    public int parameter() default -1;
    public String lockIdPath() default "";
}
