package com.example.enduser.Models;

public class User {
    private String email, name, phone_no, studentID;

    public String getEmail() {
        return email;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public User(){}
    public User(String email, String name, String phone_no) {
        this.email = email;
        this.name = name;
        this.phone_no = phone_no;
    }
}
