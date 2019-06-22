package com.example.mashaweer.model;

public class Service {

    String  uid , location , price , cost  , type ,total ,token;
    String id ;

    public Service(String uid , String location, String price, String cost , String type ,String total , String token , String id ) {
        this.uid = uid;

        this.location = location;
        this.price = price;
        this.cost = cost;
        this.type = type;
        this.total=total;
        this.token = token;
        this.id = id ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Service() {
    }

    public String getType() {
        return type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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





    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
