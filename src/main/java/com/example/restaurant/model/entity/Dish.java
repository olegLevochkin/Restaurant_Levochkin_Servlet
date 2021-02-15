package com.example.restaurant.model.entity;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class Dish {

    List<OrderDish> ordersWithDish;
    private Long id;
    private String fileName;
    private String name;
    private String nameUkr;
    private BigInteger price;
    private List<Product> productsForDish;

    public Dish() {

    }

    public List<OrderDish> getOrdersWithDish() {
        return ordersWithDish;
    }

    public void setOrdersWithDish(List<OrderDish> ordersWithDish) {
        this.ordersWithDish = ordersWithDish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameUkr() {
        return nameUkr;
    }

    public void setNameUkr(String nameUkr) {
        this.nameUkr = nameUkr;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public List<Product> getProductsForDish() {
        return productsForDish;
    }

    public void setProductsForDish(List<Product> productsForDish) {
        this.productsForDish = productsForDish;
    }

    public Dish(List<OrderDish> ordersWithDish, Long id, String fileName, String name,
                String nameUkr, BigInteger price, List<Product> productsForDish) {
        this.ordersWithDish = ordersWithDish;
        this.id = id;
        this.fileName = fileName;
        this.name = name;
        this.nameUkr = nameUkr;
        this.price = price;
        this.productsForDish = productsForDish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id.equals(dish.id) &&
                Objects.equals(fileName, dish.fileName) &&
                Objects.equals(name, dish.name) &&
                Objects.equals(nameUkr, dish.nameUkr) &&
                Objects.equals(price, dish.price) &&
                Objects.equals(productsForDish, dish.productsForDish);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, name, nameUkr, price, productsForDish);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", name='" + name + '\'' +
                ", nameUkr='" + nameUkr + '\'' +
                ", price='" + price + '\'' +
                "productsForDish" + productsForDish + '\'' +
                '}';
    }
}
