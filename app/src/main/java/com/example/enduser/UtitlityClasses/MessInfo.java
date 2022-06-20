package com.example.enduser.UtitlityClasses;

public class MessInfo {
    private String owner_name, mess_name, mess_email, mess_location, monthlyPrice, specialDishes, ratings;

    public MessInfo(){}
    public MessInfo(String owner_name, String mess_name, String mess_location, String mess_email, String monthlyPrice, String specialDishes) {
        this.owner_name = owner_name;
        this.mess_name = mess_name;
        this.mess_email = mess_email;
        this.mess_location = mess_location;
        this.monthlyPrice = monthlyPrice;
        this.specialDishes = specialDishes;
        this.ratings = "0";
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getMess_name() {
        return mess_name;
    }

    public void setMess_name(String mess_name) {
        this.mess_name = mess_name;
    }

    public String getMess_email() {
        return mess_email;
    }

    public void setMess_email(String mess_email) {
        this.mess_email = mess_email;
    }

    public String getMess_location() {
        return mess_location;
    }

    public void setMess_location(String mess_location) {
        this.mess_location = mess_location;
    }

    public String getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(String monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getSpecialDishes() {
        return specialDishes;
    }

    public void setSpecialDishes(String specialDishes) {
        this.specialDishes = specialDishes;
    }
}
