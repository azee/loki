package ru.greatbit.loki;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.greatbit.loki.mock.FakeObject;
import ru.greatbit.loki.mock.FakeService;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

/**
 * Created by azee on 13.08.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath*:beans.xml")
@SuppressWarnings("SpringJavaAutowiringInspection")
public class FakeServiceTest {

    @Autowired
    FakeService fakeService;

    @ReplaceWithMock
    @Autowired
    private LockProvider lockProvider;

    @Test
    public void testStringPaths(){
        fakeService.doSmt("Value1", "Value2");
        verify(lockProvider).getLock(argThat(is("ru.greatbit.loki.mock.FakeServiceImpl-doSmt")));

        fakeService.doSmt1(new FakeObject(), "Value2");
        verify(lockProvider).getLock(argThat(is("ru.greatbit.loki.mock.FakeServiceImpl-doSmt1")));

        FakeObject fo = new FakeObject();
        fakeService.doSmt2(new FakeObject(), fo);
        verify(lockProvider).getLock(argThat(is(fo.toString())));

        fakeService.doSmt2(new FakeObject(), new FakeObject().withId("SomeId"));
        verify(lockProvider).getLock(argThat(is("SomeId")));
    }
}
