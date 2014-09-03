package ru.greatbit.loki.mock.byinterface;

import org.springframework.stereotype.Component;
import ru.greatbit.loki.LockId;
import ru.greatbit.loki.mock.beans.FakeObject;

/**
 * Created by azee on 13.08.14.
 */
@Component
public class FakeServiceImpl implements FakeService{
    @Override
    public void doSmtParent(FakeObject valueToUpdate, FakeObject id) {

    }

    @Override
    public void doSmt(String valueToUpdate, String id) {

    }

    @Override
    public void doSmt1(FakeObject valueToUpdate, String id) {

    }

    @Override
    public void doSmt2(FakeObject valueToUpdate, @LockId FakeObject id) {

    }

    @Override
    public void doSmt3(FakeObject valueToUpdate, @LockId(path = "") String id) {

    }

    @Override
    public void doSmtWrong(String valueToUpdate, String id) {

    }

    @Override
    public void doSmtWrong1(String valueToUpdate, String id) {

    }
}
