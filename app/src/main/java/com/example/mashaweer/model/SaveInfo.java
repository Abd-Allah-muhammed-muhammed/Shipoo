package com.example.mashaweer.model;

public class SaveInfo {

    String id ,name , address,phone, mail , type_service , location_service , preice_service , location_delevry , cost_service , id_servis , token;

    public SaveInfo() {
    }

    public SaveInfo(String id, String name, String address, String phone, String mail, String type_service, String location_service, String preice_service, String location_delevry, String cost_service, String id_servis , String token) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.mail = mail;
        this.type_service = type_service;
        this.location_service = location_service;
        this.preice_service = preice_service;
        this.location_delevry = location_delevry;
        this.cost_service = cost_service;
        this.id_servis = id_servis;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getType_service() {
        return type_service;
    }

    public void setType_service(String type_service) {
        this.type_service = type_service;
    }

    public String getLocation_service() {
        return location_service;
    }

    public void setLocation_service(String location_service) {
        this.location_service = location_service;
    }

    public String getPreice_service() {
        return preice_service;
    }

    public void setPreice_service(String preice_service) {
        this.preice_service = preice_service;
    }

    public String getLocation_delevry() {
        return location_delevry;
    }

    public void setLocation_delevry(String location_delevry) {
        this.location_delevry = location_delevry;
    }

    public String getCost_service() {
        return cost_service;
    }

    public void setCost_service(String cost_service) {
        this.cost_service = cost_service;
    }

    public String getId_servis() {
        return id_servis;
    }

    public void setId_servis(String id_servis) {
        this.id_servis = id_servis;
    }
}
