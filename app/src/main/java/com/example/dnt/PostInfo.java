package com.example.dnt;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class PostInfo {
    public String restaurant;
    public String deadline_HH;
    public String deadline_mm;
    public String pickup;
    public String price;
    public String description;

    public PostInfo() {
    }

    public PostInfo(String restaurant, String deadline_HH, String deadline_mm, String pickup, String price, String description) {
        this.restaurant = restaurant;
        this.deadline_HH = deadline_HH;
        this.deadline_mm = deadline_mm;
        this.pickup = pickup;
        this.price = price;
        this.description = description;

    }


    public String getRestaurant() {return restaurant;}

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getDeadline_HH() {
        return deadline_HH;
    }

    public void setDeadline_HH(String deadline_HH) {
        this.deadline_HH = deadline_HH;
    }

    public String getDeadline_mm() {return deadline_mm;}

    public void setDeadline_mm(String deadline_mm) { this.deadline_mm = deadline_mm; }

    public String getPickup() {return pickup;}

    public void setPickup(String pickup) { this.pickup = pickup; }

    public String getPrice() {return price;}

    public void setPrice(String price) { this.price = price; }

    public String getDescription() {return description;}

    public void setDescription(String description) { this.description = description; }


    @Override
    public String toString() {
        return "post{" +
                "restaurant='" + restaurant + '\'' +
                ", deadline_HH='" + deadline_HH + '\'' +
                ", deadline_mm='" + deadline_mm + '\'' +
                ", pickup='" + pickup + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}