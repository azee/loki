package ru.greatbit.loki;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.greatbit.loki.mock.beans.FakeObject;
import ru.greatbit.loki.mock.byinterface.FakeService;
import ru.greatbit.loki.mock.byclass.FakeServiceIndividualInterface;

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

    @Autowired
    FakeServiceIndividualInterface fakeServiceIndividual;

    @ReplaceWithMock
    @Autowired
    private LockProvider lockProvider;

    @Test
    public void testLockByInterface(){
        fakeService.doSmt("Value1", "LockId1");
        verify(lockProvider).getLock(argThat(is("LockId1")));

        fakeService.doSmt1(new FakeObject(), "LockId2");
        verify(lockProvider).getLock(argThat(is("LockId2")));

        fakeService.doSmt2(new FakeObject(), new FakeObject().withId("SomeId2"));
        verify(lockProvider).getLock(argThat(is("SomeId2")));

        fakeService.doSmt3(new FakeObject(), "SomeId3");
        verify(lockProvider).getLock(argThat(is("SomeId3")));

        FakeObject fo = new FakeObject();
        fakeService.doSmtParent(new FakeObject(), fo);
        verify(lockProvider).getLock(argThat(is(fo.toString())));

        fakeService.doSmtParent(new FakeObject(), new FakeObject().withId("SomeId"));
        verify(lockProvider).getLock(argThat(is("SomeId")));

        fakeService.doSmtWrong("Value1", "LockIdWrong");
        verify(lockProvider).getLock(argThat(is("ru.greatbit.loki.mock.byinterface.FakeServiceImpl-doSmtWrong")));

        fakeService.doSmtWrong1("Value1", "LockIdWrong1");
        verify(lockProvider).getLock(argThat(is("ru.greatbit.loki.mock.byinterface.FakeServiceImpl-doSmtWrong1")));

        fakeService.doSmtNoParams();
        verify(lockProvider).getLock(argThat(is("ru.greatbit.loki.mock.byinterface.FakeServiceImpl-doSmtNoParams")));

        fakeService.doSmtNoParams1();
        verify(lockProvider).getLock(argThat(is("ru.greatbit.loki.mock.byinterface.FakeServiceImpl-doSmtNoParams1")));

    }

    @Test
    public void testLockByClass(){
        fakeServiceIndividual.doSmt("Value1", "IndividualLockId1");
        verify(lockProvider).getLock(argThat(is("IndividualLockId1")));

        fakeServiceIndividual.doSmt1(new FakeObject(), "IndividualLockId2");
        verify(lockProvider).getLock(argThat(is("IndividualLockId2")));

        FakeObject fo = new FakeObject();
        fakeServiceIndividual.doSmtParent(new FakeObject(), fo);
        verify(lockProvider).getLock(argThat(is(fo.toString())));

        fakeServiceIndividual.doSmtParent(new FakeObject(), new FakeObject().withId("SomeIdIndividual"));
        verify(lockProvider).getLock(argThat(is("SomeIdIndividual")));
    }
}
