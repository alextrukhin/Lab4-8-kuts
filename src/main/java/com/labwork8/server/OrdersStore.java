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
public class OrdersStore {
    public static final String FILE_NAME = "orders.json";

    /**
     * List of products
     */
    List<Order> data = new ArrayList<Order>();

    /**
     * Default constructor
     */
    public OrdersStore() {
        data = readFromFile(FILE_NAME);
    }

    /**
     * Get list of products
     *
     * @return list of products
     */
    public List<Order> getOrders() {
        return data;
    }

    /**
     * Get order by id
     *
     * @param id order id
     * @return order
     */
    public Order getOrderById(Integer id) {
        for (Order order : data) {
            if (Objects.equals(order.getId(), id)) {
                return order;
            }
        }
        return null;
    }

    /**
     * Add order
     *
     * @param order order
     * @return added order
     */
    public Order addOrder(Order order) {
        Integer highestId = 0;
        for (Order p : data) {
            if (p.getId() > highestId) {
                highestId = p.getId();
            }
        }
        Order newOrder = new OrderBuilder(order)
                .setId(highestId + 1)
                .build();
        data.add(newOrder);
        saveListToFile(data, FILE_NAME);
        return newOrder;
    }

    /**
     * Update order
     *
     * @param order order
     * @return order order
     */
    public Order updateOrder(Order order) {
        Integer index = null;
        for (int i = 0; i < data.size(); i++) {
            if (Objects.equals(data.get(i).getId(), order.getId())) {
                index = i;
                break;
            }
        }
        if (index == null) {
            throw new RuntimeException("Product not found");
        }
        data.set(index, order);
        saveListToFile(data, FILE_NAME);
        return order;
    }

    /**
     * Remove order
     *
     * @param id order id
     */
    public void removeOrder(Integer id) {
        data.removeIf(product -> Objects.equals(product.getId(), id));
        saveListToFile(data, FILE_NAME);
    }

    /**
     * Get orders for user
     *
     * @param email customer email
     * @return orders
     */
    public List<Order> getOrdersByUser(String email) {
        List<Order> orders = new ArrayList<Order>();
        for (Order order : data) {
            if (Objects.equals(order.getEmail(), email)) {
                orders.add(order);
            }
        }
        return orders;
    }

    /**
     * Read list of products from file
     *
     * @param fileName file name
     * @return list of products
     */
    private List<Order> readFromFile(String fileName) {
        Type REVIEW_TYPE = new TypeToken<List<Order>>() {
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
     * Save list of orders to file
     *
     * @param list     list of orders
     * @param fileName file name
     */
    private void saveListToFile(List<Order> list, String fileName) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(list, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
