package ru.greatbit.loki.mock.byclass;

import org.springframework.stereotype.Component;
import ru.greatbit.loki.Lock;
import ru.greatbit.loki.Lockable;
import ru.greatbit.loki.mock.beans.FakeObject;

/**
 * Created by azee on 13.08.14.
 */
@Lockable(locks = {
        @Lock(methodName = "doSmt", signature = {String.class, String.class}, parameter = 1, lockIdPath = ""),
        @Lock(methodName = "doSmt1", signature = {FakeObject.class, String.class}, parameter = 1, lockIdPath = ""),
        @Lock(methodName = "doSmtParent", signature = {FakeObject.class, FakeObject.class}, parameter = 1, lockIdPath = "id")

})
@Component
public class FakeServiceIndividual implements FakeServiceIndividualInterface{
    @Override
    public void doSmtParent(FakeObject valueToUpdate, FakeObject id) {

    }

    @Override
    public void doSmt(String valueToUpdate, String id) {

    }

    @Override
    public void doSmt1(FakeObject valueToUpdate, String id) {

    }
}
