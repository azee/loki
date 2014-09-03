package ru.greatbit.loki;

import java.util.concurrent.locks.Lock;

/**
 * Created by azee on 12.08.14.
 */
public interface LockProvider {
    public Lock getLock(String key);
}
