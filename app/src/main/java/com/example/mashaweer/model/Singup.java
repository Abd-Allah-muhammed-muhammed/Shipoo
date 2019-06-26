package com.example.mashaweer.model;

public class Singup {


    private String name;
    private String phone;
    private String address;
    private String pass;
    private String repass;
    private String mail;
    private String uid;
    private String token;
    private float rotate;

    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Singup(String name, String phone, String address, String pass, String repass, String mail, String uid , String token ,float rotate) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.pass = pass;
        this.repass = repass;
        this.mail = mail;
        this.uid = uid;
        this.token = token;
        this.rotate = rotate;
    }


    public Singup() {
    }

}
