package ru.greatbit.transact;

import org.springframework.stereotype.Service;

/**
 * Created by azee on 12.08.14.
 */
@Service
public class DefaultKeyProvider implements KeyProvider {
    @Override
    public String getKey(Object obj, Class clazz) {
        return null;
    }
}
