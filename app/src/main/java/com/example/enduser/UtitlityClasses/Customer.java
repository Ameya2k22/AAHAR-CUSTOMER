package com.example.enduser.UtitlityClasses;

public class Customer {

    private  String Name, Email, Password, Phone_no;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone_no() {
        return Phone_no;
    }

    public void setPhone_no(String phone_no) {
        Phone_no = phone_no;
    }

    public Customer(String name, String email, String password, String phone_no) {
        Name = name;
        Email = email;
        Password = password;
        Phone_no = phone_no;
    }

}
