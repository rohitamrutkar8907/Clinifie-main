package com.org.customer.fragment.model;

public class Date {

    String Latitude;
    String Longtitude;
    String Message;
    Long timeStamp ;

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }


    public Date() {
    }

    public Date(String latitude, String longtitude, String message) {
        Latitude = latitude;
        Longtitude = longtitude;
        Message = message;
    }



    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(String longtitude) {
        Longtitude = longtitude;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


}
