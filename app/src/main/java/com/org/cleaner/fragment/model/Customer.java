package com.org.cleaner.fragment.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Map;
import java.util.Objects;

public class Customer implements Parcelable
{

    private String id;
    private String car_color;
    private String car_model;
    private String contact_number;
    private String expiry_date;
    private String latitude;
    private String longtitude;
    private String my_cleaner;
    private String name;
    private String plan_selected;
    private String start_date;
    private String date;
    private String month;
    private String year;
    private String token;
    private String car_number;
    private Map<String,String> timStamp;


    public void setTimStamp(Map<String, String > timStamp) {
        this.timStamp = timStamp;
    }


    protected Customer(Parcel in) {
        id = in.readString();
        car_color = in.readString();
        car_model = in.readString();
        contact_number = in.readString();
        expiry_date = in.readString();
        latitude = in.readString();
        longtitude = in.readString();
        my_cleaner = in.readString();
        name = in.readString();
        plan_selected = in.readString();
        start_date = in.readString();
        date = in.readString();
        month = in.readString();
        year = in.readString();
        token = in.readString();
        car_number = in.readString();
        userId = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userId;

    public Customer()
    {

    }

    public Customer(String car_color, String car_model, String contact_number,
                    String expiry_date, String latitude, String longtitude, String my_cleaner,
                    String name, String plan_selected, String start_date) {

        this.car_color = car_color;
        this.car_model = car_model;
        this.contact_number = contact_number;
        this.expiry_date = expiry_date;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.my_cleaner = my_cleaner;
        this.name = name;
        this.plan_selected = plan_selected;
        this.start_date = start_date;
    }

    public Customer(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getCar_model() {
        return car_model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) &&
                car_color.equals(customer.car_color) &&
                car_model.equals(customer.car_model) &&
                contact_number.equals(customer.contact_number) &&
                expiry_date.equals(customer.expiry_date) &&
                latitude.equals(customer.latitude) &&
                longtitude.equals(customer.longtitude) &&
                my_cleaner.equals(customer.my_cleaner) &&
                name.equals(customer.name) &&
                plan_selected.equals(customer.plan_selected) &&
                start_date.equals(customer.start_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, car_color, car_model, contact_number,
                expiry_date, latitude, longtitude, my_cleaner, name, plan_selected, start_date);
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", car_color='" + car_color + '\'' +
                ", car_model='" + car_model + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", expiry_date='" + expiry_date + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longtitude='" + longtitude + '\'' +
                ", my_cleaner='" + my_cleaner + '\'' +
                ", name='" + name + '\'' +
                ", plan_selected='" + plan_selected + '\'' +
                ", start_date='" + start_date + '\'' +
                '}';
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getMy_cleaner() {
        return my_cleaner;
    }

    public void setMy_cleaner(String my_cleaner) {
        this.my_cleaner = my_cleaner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlan_selected() {
        return plan_selected;
    }

    public void setPlan_selected(String plan_selected) {
        this.plan_selected = plan_selected;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public static  DiffUtil.ItemCallback<Customer> itemCallback =
            new DiffUtil.ItemCallback<Customer>() {
                @Override
                public boolean areItemsTheSame(@NonNull Customer oldItem, @NonNull Customer newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Customer oldItem, @NonNull Customer newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(car_color);
        dest.writeString(car_model);
        dest.writeString(contact_number);
        dest.writeString(expiry_date);
        dest.writeString(latitude);
        dest.writeString(longtitude);
        dest.writeString(my_cleaner);
        dest.writeString(name);
        dest.writeString(plan_selected);
        dest.writeString(start_date);
        dest.writeString(date);
        dest.writeString(month);
        dest.writeString(year);
        dest.writeString(token);
        dest.writeString(car_number);
        dest.writeString(userId);
    }
}