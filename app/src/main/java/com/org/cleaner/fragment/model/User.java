package com.org.cleaner.fragment.model;

public class User {


    private String name;
    private String car_model;
    private String plan_selected;
   /* private String userName;
    private String password; */

    public User() {
    }

    public User(String name,String car_model,String plan_selected) {
        this.name = name;
        this.car_model = car_model;
        this.plan_selected=plan_selected;
       /* this.userName = userName;
        this.password = password; */
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getPlan_selected() {
        return plan_selected;
    }

    public void setPlan_selected(String plan_selected) {
        this.plan_selected = plan_selected;
    }


    /*public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    } */
}
