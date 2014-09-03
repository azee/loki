package ru.greatbit.loki.mock.beans;

/**
 * Created by azee on 13.08.14.
 */
public class FakeObject {
    private String id;
    private String value;
    private FakeObject fakeObject;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FakeObject getFakeObject() {
        return fakeObject;
    }

    public void setFakeObject(FakeObject fakeObject) {
        this.fakeObject = fakeObject;
    }

    public FakeObject withId(String id){
        setId(id);
        return this;
    }
}
