package com.example.mashaweer.model;

public class AcceptService {

    private String idItem ;
    private String type ;
   private String token;
   private String tokenUser ;

    public AcceptService(String idItem, String type, String token , String tokenUser) {
        this.idItem = idItem;
        this.type = type;
        this.token = token;
        this.tokenUser= tokenUser;
    }

    public String getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(String tokenUser) {
        this.tokenUser = tokenUser;
    }

    public AcceptService() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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


}
