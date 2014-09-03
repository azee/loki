package ru.greatbit.loki.mock;

import org.springframework.stereotype.Service;

/**
 * Created by azee on 13.08.14.
 */
@Service
public interface FakeServiceBase {
    public void doSmtParent(FakeObject valueToUpdate, FakeObject id);
}
