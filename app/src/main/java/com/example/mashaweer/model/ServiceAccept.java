package com.example.mashaweer.model;

public class ServiceAccept {

    private String idItem ;
    private String type ;
    private String uid ;
    private String uid2 ;

    public ServiceAccept(String idItem, String type, String uid , String uid2) {
        this.idItem = idItem;
        this.type = type;
        this.uid = uid;
        this.uid2 = uid2;
    }

    public ServiceAccept() {
    }

    public String getUid2() {
        return uid2;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
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
