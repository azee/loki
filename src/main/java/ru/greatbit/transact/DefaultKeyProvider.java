package ru.greatbit.transact;

import org.springframework.stereotype.Service;
import ru.greatbit.transact.data.TransactionMethodMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by azee on 12.08.14.
 */
@Service
public class DefaultKeyProvider implements KeyProvider {
    private final String DELIM = "-";

    @Override
    public String getKey(Object obj, Class clazz, Method method, Object[] args, TransactionMethodMeta meta) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (meta == null || meta.getIdPath() == null || "".equals(meta.getIdPath())
                || args.length == 0 || meta.getIdArgumentIndex() >= args.length){
            return returnDefault(clazz, method);
        }


        Object arg = args[meta.getIdArgumentIndex()];
        if (arg == null){
            return returnDefault(clazz, method);
        }

        List<String> path = new LinkedList<String>();
        path.addAll(Arrays.asList(meta.getIdPath().split(".")));

        return findValue(obj, clazz, path);
    }

    private String returnDefault(Class clazz, Method method){
        return clazz.getName() + DELIM + method.getName();
    }

    private String findValue(Object object, Class clazz, List<String> path){
        if (path.size() != 0){
            path.remove(0);
            Object nextObject = getObjectFromField(object, clazz, path.get(0).trim());
            if (nextObject != null){
                return findValue(nextObject, clazz, path);
            }
        }
        if (object == null){
            return clazz.getName();
        }
        return object.toString();
    }

    private Object getObjectFromField(Object obj, Class clazz, String fieldName){
        Object result;
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }

        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            result = field.get(obj);
            field.setAccessible(accessible);
            return result;

        } catch (IllegalAccessException e) {
            return null;
        }
    }


}
