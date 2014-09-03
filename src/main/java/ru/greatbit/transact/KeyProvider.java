package ru.greatbit.transact;

import ru.greatbit.transact.data.TransactionMethodMeta;
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

    public static String getKey(Class clazz, Method method, Object[] args, TransactionMethodMeta meta) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (meta == null || meta.getIdPath() == null || "".equals(meta.getIdPath())
                || args.length == 0 || meta.getIdArgumentIndex() >= args.length){
            return returnDefault(clazz, method);
        }


        Object arg = args[meta.getIdArgumentIndex()];
        if (arg == null){
            return returnDefault(clazz, method);
        }

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
