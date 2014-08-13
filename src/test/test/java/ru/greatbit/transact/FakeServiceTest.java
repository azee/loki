package ru.greatbit.transact;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.greatbit.transact.mock.FakeObject;
import ru.greatbit.transact.mock.FakeServiceImpl;

/**
 * Created by azee on 13.08.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:beans.xml")
@SuppressWarnings("SpringJavaAutowiringInspection")
public class FakeServiceTest {
    @Autowired
    FakeServiceImpl fakeServiceImpl;

    @Test
    public void testStringPaths(){
        fakeServiceImpl.doSmt("Value1", "Value2");
        fakeServiceImpl.doSmt1(new FakeObject(), "Value2");
        fakeServiceImpl.doSmt2(new FakeObject(), new FakeObject());
        fakeServiceImpl.doSmt2(new FakeObject(), new FakeObject().withId("Value2"));
    }
}
