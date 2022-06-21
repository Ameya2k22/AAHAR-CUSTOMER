package com.example.enduser.Models;

import com.example.enduser.Adapter.UserDisplayAdapter;

public class UserDisplayModel {
    int image;
    String name, phone_no, email;

    public UserDisplayModel(){}

    public UserDisplayModel(int image, String name, String phone_no, String email) {
        this.image = image;
        this.name = name;
        this.phone_no = phone_no;
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
