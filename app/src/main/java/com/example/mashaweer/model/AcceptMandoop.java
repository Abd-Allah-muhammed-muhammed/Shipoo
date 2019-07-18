package com.example.mashaweer.model;

public class AcceptMandoop {

    private  String idItem , tokenUser ,tokenMandoop;

    public AcceptMandoop(String idItem, String tokenUser , String tokenMandoop) {
        this.idItem = idItem;
        this.tokenUser = tokenUser;
        this.tokenMandoop = tokenMandoop;
    }

    public AcceptMandoop() {
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(String tokenUser) {
        this.tokenUser = tokenUser;
    }

    public String getTokenMandoop() {
        return tokenMandoop;
    }

    public void setTokenMandoop(String tokenMandoop) {
        this.tokenMandoop = tokenMandoop;
    }
}
