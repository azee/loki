package ru.greatbit.loki.mock.byinterface;

import org.springframework.stereotype.Service;
import ru.greatbit.loki.Lock;
import ru.greatbit.loki.Lockable;
import ru.greatbit.loki.mock.beans.FakeObject;

/**
 * Created by azee on 13.08.14.
 */
@Lockable(locks = {
        @Lock(methodName = "doSmt", signature = {String.class, String.class}, parameter = 1, lockIdPath = ""),
        @Lock(methodName = "doSmt1", signature = {FakeObject.class, String.class}, parameter = 1, lockIdPath = ""),
        @Lock(methodName = "doSmtWrong", signature = {String.class, String.class}, parameter = 3, lockIdPath = ""),
        @Lock(methodName = "doSmtWrong1", signature = {String.class, String.class}),
        @Lock(methodName = "doSmtNoParams"),
        @Lock(methodName = "doSmtParent", signature = {FakeObject.class, FakeObject.class}, parameter = 1, lockIdPath = "id")
})
@Service
public interface FakeService extends FakeServiceBase{
    public void doSmt(String valueToUpdate, String id);
    public void doSmt1(FakeObject valueToUpdate, String id);
    public void doSmt2(FakeObject valueToUpdate, FakeObject id);
    public void doSmt3(FakeObject valueToUpdate, String id);
    public void doSmtWrong(String valueToUpdate, String id);
    public void doSmtWrong1(String valueToUpdate, String id);
    public void doSmtNoParams();
    public void doSmtNoParams1();
}
