package com.org.customer.fragment.model;

public class Cleaner {
    private String name;
    private String contact_number;
    private String address;
    private String image_url;
    private String userid;


    public Cleaner(String name, String image_url, String address, String contact_number, String userid) {
        this.name = name;
        this.image_url = image_url;
        this.address = address;
        this.contact_number = contact_number;
        this.userid = userid;
    }

    public Cleaner() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
