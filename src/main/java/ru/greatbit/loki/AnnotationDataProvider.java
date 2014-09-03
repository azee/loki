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
    public static Lock[] getLocks(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Annotation lockable = getAnnotationFromInterfaces(clazz, Lockable.class);
        if (lockable == null){
            lockable = clazz.getAnnotation(Lockable.class);
        }
        if (lockable == null) {
            return new Lock[]{};
        }
        Method method = Lockable.class.getMethod("locks", (Class[]) null);
        return (Lock[]) method.invoke(lockable, (Object[])null);
    }

    public static Map<String, MethodMeta> getLockMethodsMeta(Lock[] locks){
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
