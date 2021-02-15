package com.example.restaurant.model.entity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Product {

    private Long id;
    private String product;
    private int amountHave;
    private int maxAmount;
    private int minAmount;
    private int productInBox;
    private Set<User> userLike;
    private List<Dish> dishes;

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getAmountHave() {
        return amountHave;
    }

    public void setAmountHave(int amountHave) {
        this.amountHave = amountHave;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getProductInBox() {
        return productInBox;
    }

    public void setProductInBox(int productInBox) {
        this.productInBox = productInBox;
    }

    public Set<User> getUserLike() {
        return userLike;
    }

    public void setUserLike(Set<User> userLike) {
        this.userLike = userLike;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Product(Long id, String product, int amountHave, int maxAmount, int minAmount, int productInBox, Set<User> userLike, List<Dish> dishes) {
        this.id = id;
        this.product = product;
        this.amountHave = amountHave;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.productInBox = productInBox;
        this.userLike = userLike;
        this.dishes = dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product prod = (Product) o;
        return id == prod.id &&
                product.equals(prod.product) &&
                Objects.equals(amountHave, prod.amountHave) &&
                Objects.equals(maxAmount, prod.maxAmount) &&
                Objects.equals(minAmount, prod.minAmount) &&
                Objects.equals(productInBox, prod.productInBox) &&
                Objects.equals(userLike, prod.userLike) &&
                Objects.equals(dishes, prod.dishes);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, amountHave, maxAmount, minAmount, productInBox, userLike, dishes);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", product='" + product + '\'' +
                ", amountHave='" + amountHave + '\'' +
                ", maxAmount='" + maxAmount + '\'' +
                ", minAmount='" + minAmount + '\'' +
                ", productInBox='" + productInBox + '\'' +
                ", userLike=" + userLike +
                ", dishes=" + dishes +
                '}';
    }
}
