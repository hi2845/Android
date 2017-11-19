package com.example.hi284.androidproject;

/**
 * Created by hi284 on 2017-11-19.
 */

public class RestaurantClass {
    private int _id;

    private String name;
    private String phone;
    private String address;
    private String pic;

    public RestaurantClass(){}
    public RestaurantClass(int _id)
    {
        this._id=_id;
    }

    public int get_id() { return _id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress(){return address;}
    public String getPic(){return pic;}

    public void set_id(int _id) { this._id = _id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String phone) { this.address = address; }
    public void setPic(String phone) { this.pic = pic; }






}
