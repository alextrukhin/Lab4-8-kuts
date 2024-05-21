package com.labwork8.server;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    private Integer id;
    private List<OrderProduct> products;
    private Integer totalPrice;
    private String trackingNumber;
    public enum Status {
        NEW, DELIVERING, COMPLETED, CANCELED
    }
    private Status status;
    private String name;
    private String email;
    private String phone;

    public Order() {
    }

    public Order(Integer id, List<OrderProduct> products, Integer totalPrice, String trackingNumber, Status status, String name, String email, String phone) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @SerializedName("id")
    public Integer getId() {
        return id;
    }

    @SerializedName("products")
    public List<OrderProduct> getProducts() {
        return products;
    }

    @SerializedName("totalPrice")
    public Integer getTotalPrice() {
        return totalPrice;
    }

    @SerializedName("trackingNumber")
    public String getTrackingNumber() {
        return trackingNumber;
    }

    @SerializedName("status")
    public Status getStatus() {
        return status;
    }

    @SerializedName("name")
    public String getName() {
        return name;
    }

    @SerializedName("email")
    public String getEmail() {
        return email;
    }

    @SerializedName("phone")
    public String getPhone() {
        return phone;
    }
}
