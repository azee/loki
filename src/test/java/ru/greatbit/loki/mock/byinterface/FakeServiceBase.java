package ru.greatbit.loki.mock.byinterface;

import org.springframework.stereotype.Service;
import ru.greatbit.loki.mock.beans.FakeObject;

/**
 * Created by azee on 13.08.14.
 */
@Service
public interface FakeServiceBase {
    public void doSmtParent(FakeObject valueToUpdate, FakeObject id);
}
