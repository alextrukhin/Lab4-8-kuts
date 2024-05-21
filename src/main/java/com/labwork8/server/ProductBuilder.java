package com.labwork8.server;

/**
 * ProductBuilder class is used to create Product objects
 */
public class ProductBuilder {
    private Integer id;
    private String name;
    private Product.Color color;
    private Integer price;
    private String manufacturer;
    private String description;
    private String image;
    private Integer quantity;

    public ProductBuilder() {
    }

    public ProductBuilder(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.color = product.getColor();
        this.price = product.getPrice();
        this.manufacturer = product.getManufacturer();
        this.description = product.getDescription();
        this.image = product.getImage();
        this.quantity = product.getQuantity();
    }

    public ProductBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setColor(Product.Color color) {
        this.color = color;
        return this;
    }

    public ProductBuilder setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public ProductBuilder setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Product build() {
        return new Product(id, name, color, price, manufacturer, description, image, quantity);
    }
}
