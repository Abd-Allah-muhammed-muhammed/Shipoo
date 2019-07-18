package com.example.mashaweer.model;

public class Users {

    private String name;
    private String phone;
    private String address;
    private String pass;
    private String repass;
    private String mail;
    private String token;
    private float rotate;
    private  int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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





    public Users(String name, String phone, String address, String pass, String repass, String mail, String token , float rotate , int id) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.pass = pass;
        this.repass = repass;
        this.mail = mail;
        this.token = token;
        this.rotate = rotate;
        this.id = id ;
    }


    public Users() {
    }

}
