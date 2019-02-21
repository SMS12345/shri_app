package com.example.sms.shri_login;

public class User {
    public String name,email,mobile,blood_group,eno1,eno2,eno3,vin;
    public User() {}
    public User(String name, String email, String mobile, String blood_group, String eno1, String eno2, String eno3)
    {
        this.name=name;
        this.email=email;
        this.mobile=mobile;
        this.blood_group=blood_group;
        this.eno1=eno1;
        this.eno2=eno2;
        this.eno3=eno3;
        this.vin="0";
    }
}
