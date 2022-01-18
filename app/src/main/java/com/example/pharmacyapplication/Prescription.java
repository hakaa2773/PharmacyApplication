package com.example.pharmacyapplication;

public class Prescription {
    private Integer id;
    private String email;
    private String phone;
    private String address;
    private String drugs;

    public Prescription() {
    }

    public Prescription(String email, String phone, String address, String drugs) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.drugs = drugs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
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

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }
}
