package com.org.customer.fragment.model;

public class Plan {


    private String days;
    private String desc;
    private String rs;

    public Plan(String days, String desc, String rs) {
        this.days = days;
        this.desc = desc;
        this.rs = rs;
    }

    public Plan()
    {

    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }


}
