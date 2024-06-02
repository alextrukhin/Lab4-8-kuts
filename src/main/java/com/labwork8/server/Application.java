package com.labwork8.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Application {
    private final String ORIGIN = "http://localhost:5174";

    /**
     * ProductsStore instance
     */
    private final ProductsStore productsStore = new ProductsStore();
    private final OrdersStore ordersStore = new OrdersStore();

    /**
     * Index page
     *
     * @return greetings
     */
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    /**
     * Get all products
     *
     * @return list of products
     */
    @GetMapping(path = "/getProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> getProducts() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return new ResponseEntity<Object>(gson.toJson(productsStore.getProducts()), HttpStatus.OK);
    }

    /**
     * Create product
     *
     * @param datamap product object
     * @return product
     */
    @PostMapping(path = "/createProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> createProduct(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        ProductBuilder productBuilder = new ProductBuilder();

        productBuilder.setId(0)
                .setName(datamap.get("name").toString())
                .setColor(Product.Color.valueOf(datamap.get("color").toString()))
                .setPrice(Integer.parseInt(datamap.get("price").toString()))
                .setManufacturer(datamap.get("manufacturer").toString())
                .setDescription(datamap.get("description").toString())
                .setImage(datamap.get("image").toString())
                .setQuantity(Integer.parseInt(datamap.get("quantity").toString()));

        Product product = productsStore.addProduct(productBuilder.build());
        return new ResponseEntity<Object>(gson.toJson(product), HttpStatus.OK);
    }

    /**
     * Update product
     *
     * @param datamap product object
     * @return product
     */
    @PostMapping(path = "/updateProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> updateProduct(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Product product = productsStore.getProductById(Integer.parseInt(datamap.get("id").toString()));
        ProductBuilder productBuilder = new ProductBuilder(product);

        if (datamap.get("name") != null) {
            productBuilder.setName(datamap.get("name").toString());
        }
        if (datamap.get("color") != null) {
            productBuilder.setColor(Product.Color.valueOf(datamap.get("color").toString()));
        }
        if (datamap.get("price") != null) {
            productBuilder.setPrice(Integer.parseInt(datamap.get("price").toString()));
        }
        if (datamap.get("manufacturer") != null) {
            productBuilder.setManufacturer(datamap.get("manufacturer").toString());
        }
        if (datamap.get("description") != null) {
            productBuilder.setDescription(datamap.get("description").toString());
        }
        if (datamap.get("image") != null) {
            productBuilder.setImage(datamap.get("image").toString());
        }

        return new ResponseEntity<Object>(gson.toJson(productsStore.updateProduct(productBuilder.build())), HttpStatus.OK);
    }

    /**
     * Delete product
     *
     * @param datamap product object
     * @return product
     */
    @DeleteMapping(path = "/deleteProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> deleteProduct(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Product product = productsStore.getProductById(Integer.parseInt(datamap.get("id").toString()));
        productsStore.removeProduct(product.getId(), ordersStore.getOrders());
        return new ResponseEntity<Object>(gson.toJson(product), HttpStatus.OK);
    }

    /**
     * Get all orders
     *
     * @return list of orders
     */
    @GetMapping(path = "/getOrders", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> getOrders() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return new ResponseEntity<Object>(gson.toJson(ordersStore.getOrders()), HttpStatus.OK);
    }

    /**
     * Get my orders
     *
     * @return list of orders
     */
    @PostMapping(path = "/getMyOrders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> getMyOrders(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return new ResponseEntity<Object>(gson.toJson(ordersStore.getOrdersByUser(datamap.get("email").toString())), HttpStatus.OK);
    }

    /**
     * Create order
     *
     * @param datamap order object
     * @return order
     */
    @PostMapping(path = "/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> createOrder(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        OrderBuilder orderBuilder = new OrderBuilder();

        orderBuilder.setId(0)
                .setProducts(((ArrayList<LinkedHashMap<String, ?>>) datamap.get("products")).stream().map(p -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProductId(Integer.parseInt(p.get("productId").toString()));
                    orderProduct.setQuantity(Integer.parseInt(p.get("quantity").toString()));
                    return orderProduct;
                }).collect(Collectors.toList()))
                .setTotalPrice(Integer.parseInt(datamap.get("totalPrice").toString()))
                .setTrackingNumber(datamap.get("trackingNumber").toString())
                .setStatus(Order.Status.valueOf(datamap.get("status").toString()))
                .setName(datamap.get("name").toString())
                .setEmail(datamap.get("email").toString())
                .setPhone(datamap.get("phone").toString());

        Order order = ordersStore.addOrder(orderBuilder.build());
        return new ResponseEntity<Object>(gson.toJson(order), HttpStatus.OK);
    }

    /**
     * Update order
     *
     * @param datamap order object
     * @return order
     */
    @PostMapping(path = "/updateOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> updateOrder(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Order order = ordersStore.getOrderById(Integer.parseInt(datamap.get("id").toString()));
        OrderBuilder orderBuilder = new OrderBuilder(order);

        if (datamap.get("products") != null) {
            orderBuilder.setProducts(((ArrayList<LinkedHashMap<String, ?>>) datamap.get("products")).stream().map(p -> {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setProductId(Integer.parseInt(p.get("productId").toString()));
                orderProduct.setQuantity(Integer.parseInt(p.get("quantity").toString()));
                return orderProduct;
            }).collect(Collectors.toList()));
        }
        if (datamap.get("totalPrice") != null) {
            orderBuilder.setTotalPrice(Integer.parseInt(datamap.get("totalPrice").toString()));
        }
        if (datamap.get("trackingNumber") != null) {
            orderBuilder.setTrackingNumber(datamap.get("trackingNumber").toString());
        }
        if (datamap.get("status") != null) {
            orderBuilder.setStatus(Order.Status.valueOf(datamap.get("status").toString()));
        }
        if (datamap.get("name") != null) {
            orderBuilder.setName(datamap.get("name").toString());
        }
        if (datamap.get("email") != null) {
            orderBuilder.setEmail(datamap.get("email").toString());
        }
        if (datamap.get("phone") != null) {
            orderBuilder.setPhone(datamap.get("phone").toString());
        }

        return new ResponseEntity<Object>(gson.toJson(ordersStore.updateOrder(orderBuilder.build())), HttpStatus.OK);
    }

    /**
     * Delete order
     *
     * @param datamap order object
     * @return order
     */
    @PostMapping(path = "/deleteOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> deleteOrder(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Order order = ordersStore.getOrderById(Integer.parseInt(datamap.get("id").toString()));
        ordersStore.removeOrder(order.getId());
        return new ResponseEntity<Object>(gson.toJson(order), HttpStatus.OK);
    }
}
