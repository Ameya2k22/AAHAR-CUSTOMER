package com.example.enduser.Models;

public class UserDisplayModel {
    int image;
    String name, mobile, email;

    public UserDisplayModel(int image, String name, String mobile, String email) {
        this.image = image;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
