package com.labwork8.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * ProductsStore class is used to store information about products
 */
public class ProductsStore {
    public static final String FILE_NAME = "products.json";

    /**
     * List of products
     */
    List<Product> data = new ArrayList<Product>();

    /**
     * Default constructor
     */
    public ProductsStore() {
        data = readFromFile(FILE_NAME);
    }

    /**
     * Get list of products
     *
     * @return list of products
     */
    public List<Product> getProducts() {
        return data;
    }

    /**
     * Get product by id
     *
     * @param id product id
     * @return product
     */
    public Product getProductById(String id) {
        for (Product product : data) {
            if (Objects.equals(product.getId(), id)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Add product
     *
     * @param product product
     * @return added product
     */
    public Product addProduct(Product product) {
        Integer highestId = 0;
        for (Product p : data) {
            if (p.getId() > highestId) {
                highestId = p.getId();
            }
        }
        Product newProduct = new ProductBuilder(product)
                .setId(highestId)
                .build();
        data.add(newProduct);
        saveListToFile(data, FILE_NAME);
        return newProduct;
    }

    /**
     * Update product
     *
     * @param product product
     * @return updated product
     */
    public Product updateProduct(Product product) {
        Integer index = null;
        for (int i = 0; i < data.size(); i++) {
            if (Objects.equals(data.get(i).getId(), product.getId())) {
                index = i;
                break;
            }
        }
        if (index == null) {
            throw new RuntimeException("Product not found");
        }
        data.set(index, product);
        saveListToFile(data, FILE_NAME);
        return product;
    }

    /**
     * Remove product
     *
     * @param id       product id
     * @param products list of products
     */
    public void removeProduct(Integer id, List<Product> products) {
        data.removeIf(product -> Objects.equals(product.getId(), id));
        saveListToFile(data, FILE_NAME);
    }

    /**
     * Read list of products from file
     *
     * @param fileName file name
     * @return list of products
     */
    private List<Product> readFromFile(String fileName) {
        Type REVIEW_TYPE = new TypeToken<List<Product>>() {
        }.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            saveListToFile(data, fileName);
            return data;
        }
        return gson.fromJson(reader, REVIEW_TYPE);
    }

    /**
     * Save list of products to file
     *
     * @param list     list of products
     * @param fileName file name
     */
    private void saveListToFile(List<Product> list, String fileName) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(list, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
