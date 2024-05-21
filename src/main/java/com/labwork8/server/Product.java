package com.labwork8.server;

import com.google.gson.annotations.SerializedName;

public class Product {
    private Integer id;
    private String name;
    public enum Color {
        RED, GREEN, BLUE, ORANGE, YELLOW, BLACK, WHITE
    }
    private Color color;
    private Integer price;
    private String manufacturer;
    private String description;
    private String image;
    private Integer quantity;

    public Product() {
    }

    public Product(Integer id, String name, Color color, Integer price, String manufacturer, String description, String image, Integer quantity) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
        this.manufacturer = manufacturer;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
    }

    @SerializedName("id")
    public Integer getId() {
        return id;
    }

    @SerializedName("name")
    public String getName() {
        return name;
    }

    @SerializedName("color")
    public Color getColor() {
        return color;
    }

    @SerializedName("price")
    public Integer getPrice() {
        return price;
    }

    @SerializedName("manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    @SerializedName("description")
    public String getDescription() {
        return description;
    }

    @SerializedName("image")
    public String getImage() {
        return image;
    }

    @SerializedName("quantity")
    public Integer getQuantity() {
        return quantity;
    }
}
