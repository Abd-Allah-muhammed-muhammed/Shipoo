package com.example.mashaweer.model;

public class Service {

    String  from ,to , price , cost  , type  ,token  , time;
    String id ;
    boolean spacial;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Service( String from, String price, String cost , String type   , String token , String id  , String to , boolean spacial , String time) {


        this.from = from;
        this.price = price;
        this.cost = cost;
        this.type = type;
        this.token = token;
        this.id = id ;
        this.to= to;
        this.spacial=spacial;
        this.time = time;
    }

    public boolean isSpacial() {
        return spacial;
    }

    public void setSpacial(boolean spacial) {
        this.spacial = spacial;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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



    public void setType(String type) {
        this.type = type;
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
