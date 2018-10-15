package ru.store.dto;

public class ResultDTO {

    private Object object;
    private Boolean bool;


    public ResultDTO()  {

    }

    public ResultDTO(Object obj) {
        this.object = obj;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
