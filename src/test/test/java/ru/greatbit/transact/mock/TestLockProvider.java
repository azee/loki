package ru.greatbit.transact.mock;

import ru.greatbit.transact.LockProvider;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by azee on 13.08.14.
 */
public class TestLockProvider implements LockProvider {
    @Override
    public Lock getLock(String key) {
        return new ReentrantLock();
    }
}
