package com.labwork8.server;

import java.util.List;

public class OrderBuilder {
    private Integer id;
    private List<OrderProduct> products;
    private Integer totalPrice;
    private String trackingNumber;
    private Order.Status status;
    private String name;
    private String email;
    private String phone;

    public OrderBuilder() {
    }

    public OrderBuilder(Order order) {
        this.id = order.getId();
        this.products = order.getProducts();
        this.totalPrice = order.getTotalPrice();
        this.trackingNumber = order.getTrackingNumber();
        this.status = order.getStatus();
        this.name = order.getName();
        this.email = order.getEmail();
        this.phone = order.getPhone();
    }

    public OrderBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setProducts(List<OrderProduct> products) {
        this.products = products;
        return this;
    }

    public OrderBuilder setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderBuilder setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public OrderBuilder setStatus(Order.Status status) {
        this.status = status;
        return this;
    }

    public OrderBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public OrderBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public OrderBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Order build() {
        return new Order(id, products, totalPrice, trackingNumber, status, name, email, phone);
    }
}
