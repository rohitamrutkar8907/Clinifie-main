package com.org.cleaner.fragment.model;

import java.util.Map;

public class Date {

    String Latitude;
    String Longtitude;
    String Message;

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

    public Map<String, String> getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Map<String, String> timeStamp) {
        TimeStamp = timeStamp;
    }

    Map<String, String> TimeStamp;



}
