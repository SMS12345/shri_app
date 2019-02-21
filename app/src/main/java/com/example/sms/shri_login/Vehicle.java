package com.example.sms.shri_login;

public class Vehicle {
    public String model,color,owner;
    public GPS gps;
    public Vehicle(){}
    public Vehicle(String model,String color,String owner)
    {
        this.model=model;
        this.color=color;
        this.owner=owner;
        this.gps=new GPS();

    }
}
