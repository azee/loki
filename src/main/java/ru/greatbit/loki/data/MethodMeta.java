package ru.greatbit.loki.data;

/**
 * Created by azee on 13.08.14.
 */
public class MethodMeta {
    private String name;
    private int idArgumentIndex;
    private String idPath;
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdArgumentIndex() {
        return idArgumentIndex;
    }

    public void setIdArgumentIndex(int idArgumentIndex) {
        this.idArgumentIndex = idArgumentIndex;
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }
}
