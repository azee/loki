package ru.greatbit.loki;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.greatbit.loki.mock.FakeObject;
import ru.greatbit.loki.mock.FakeService;

/**
 * Created by azee on 13.08.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:beans.xml")
@SuppressWarnings("SpringJavaAutowiringInspection")
public class FakeServiceTest {
    @Autowired
    FakeService fakeService;

    @Test
    public void testStringPaths(){
        fakeService.doSmt("Value1", "Value2");
        fakeService.doSmt1(new FakeObject(), "Value2");
        fakeService.doSmt2(new FakeObject(), new FakeObject());
        fakeService.doSmt2(new FakeObject(), new FakeObject().withId("SomeId"));
    }
}
