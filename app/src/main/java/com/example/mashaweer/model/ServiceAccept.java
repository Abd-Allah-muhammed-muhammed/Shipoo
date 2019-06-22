package com.example.mashaweer.model;

public class ServiceAccept {

    private String idItem ;
    private String type ;
    private String uid ;

    public ServiceAccept(String idItem, String type, String uid) {
        this.idItem = idItem;
        this.type = type;
        this.uid = uid;
    }

    public ServiceAccept() {
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
