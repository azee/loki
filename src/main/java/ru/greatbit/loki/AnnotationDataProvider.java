package ru.greatbit.loki;

import org.springframework.stereotype.Service;
import ru.greatbit.loki.data.MethodMeta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by azee on 13.08.14.
 */
@Service
public class AnnotationDataProvider {
    public static Map<String, MethodMeta> getLockMethodsMeta(Class clazz){
        Map<String, MethodMeta> methods = new HashMap<String, MethodMeta>();

        //Collect meta from class annotations
        for (Lock lock : getClassLocks(clazz)){
            MethodMeta methodMeta = new MethodMeta();
            methodMeta.setName(lock.methodName());
            methodMeta.setIdArgumentIndex(lock.parameter());
            methodMeta.setIdPath(lock.lockIdPath());
            methodMeta.setSignature(Arrays.asList(lock.signature()).toString());
            methods.put(String.format("%s-%s", methodMeta.getName(), methodMeta.getSignature()), methodMeta);
        }

        //Collect meta from methods annotations
        for (Method method : clazz.getDeclaredMethods()){
            if (method.isAnnotationPresent(Lock.class)){
                MethodMeta methodMeta = new MethodMeta();
                methodMeta.setName(method.getName());
                methodMeta.setSignature(Arrays.asList(method.getParameterTypes()).toString());

                //Get data from LockId annotation
                Annotation[][] annotations = method.getParameterAnnotations();
                for (int i = 0; i < annotations.length; i++){
                    for (Annotation annotation : annotations[i]){
                        if (annotation instanceof LockId){
                            methodMeta.setIdArgumentIndex(i);
                            methodMeta.setIdPath(((LockId) annotation).path());
                        }
                    }
                }
                methods.put(String.format("%s-%s", methodMeta.getName(), methodMeta.getSignature()), methodMeta);
            }
        }

        return methods;
    }

    public static Lock[] getClassLocks(Class clazz){
        Annotation lockable = getAnnotationFromInterfaces(clazz, Lockable.class);
        if (lockable == null){
            lockable = clazz.getAnnotation(Lockable.class);
        }
        if (lockable == null) {
            return new Lock[]{};
        }
        try {
            Method method = Lockable.class.getMethod("locks", (Class[]) null);
            return (Lock[]) method.invoke(lockable, (Object[])null);
        } catch (Exception e){
            return new Lock[]{};
        }

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
