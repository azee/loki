package ru.greatbit.loki.mock;

import org.springframework.stereotype.Service;
import ru.greatbit.loki.Lock;
import ru.greatbit.loki.LockId;
import ru.greatbit.loki.Lockable;

/**
 * Created by azee on 13.08.14.
 */
@Lockable(locks = {
        @Lock(methodName = "doSmt", parameter = 1, lockIdPath = ""),
        @Lock(methodName = "doSmt1", parameter = 1, lockIdPath = ""),
        @Lock(methodName = "doSmt2"),
        @Lock(methodName = "doSmt3"),
        @Lock(methodName = "doSmtWrong", parameter = 3, lockIdPath = ""),
        @Lock(methodName = "doSmtWrong1"),
        @Lock(methodName = "doSmtParent", parameter = 1, lockIdPath = "id")
})
@Service
public interface FakeService extends FakeServiceBase{
    public void doSmt(String valueToUpdate, String id);
    public void doSmt1(FakeObject valueToUpdate, String id);
    public void doSmt2(FakeObject valueToUpdate, @LockId(path = "id") FakeObject id);
    public void doSmt3(FakeObject valueToUpdate, @LockId(path = "") String id);
    public void doSmtWrong(String valueToUpdate, String id);
    public void doSmtWrong1(String valueToUpdate, String id);
}
