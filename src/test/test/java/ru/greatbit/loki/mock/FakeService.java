package ru.greatbit.loki.mock;

import org.springframework.stereotype.Service;
import ru.greatbit.loki.Lock;
import ru.greatbit.loki.Lockable;

/**
 * Created by azee on 13.08.14.
 */
@Lockable(locks = {
        @Lock(methodName = "doSmt", parameter = 1, lockIdPath = ""),
        @Lock(methodName = "doSmt1", parameter = 1, lockIdPath = ""),
        @Lock(methodName = "doSmt2", parameter = 1, lockIdPath = "id")
})
@Service
public interface FakeService extends FakeServiceBase{
    public void doSmt(String valueToUpdate, String id);
    public void doSmt1(FakeObject valueToUpdate, String id);
}
