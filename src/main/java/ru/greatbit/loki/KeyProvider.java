package ru.greatbit.loki;

import ru.greatbit.loki.data.MethodMeta;
import ru.greatbit.utils.refclection.FieldsFetcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by azee on 12.08.14.
 */
public class KeyProvider {
    private static final String DELIM = "-";

    public static String getKey(Class clazz, Method method, Object[] args, MethodMeta meta) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Ignore incorrect parameters
        if (meta == null || args == null || args.length == 0){
            return returnDefault(clazz, method);
        }

        //Couldn't find parameter containing lock id
        if (meta.getIdArgumentIndex() >= args.length || meta.getIdArgumentIndex() < 0
                || meta.getIdPath() == null ){
            return returnDefault(clazz, method);
        }

        //Get an argument
        Object arg = args[meta.getIdArgumentIndex()];
        if (arg == null){
            return returnDefault(clazz, method);
        }

        //Find a value in the argiment
        List<String> path = new LinkedList<String>();
        path.addAll(Arrays.asList(meta.getIdPath().split("/.")));

        try {
            return FieldsFetcher.findValue(arg, Arrays.asList(meta.getIdPath().split("/."))).toString();
        } catch (InstantiationException e) {
            return returnDefault(clazz, method);
        }
    }

    private static String returnDefault(Class clazz, Method method){
        return clazz.getName() + DELIM + method.getName();
    }
}
