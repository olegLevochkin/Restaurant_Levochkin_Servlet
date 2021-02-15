package com.example.restaurant.model.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OrderDish {

    private List<Dish> dishes = Collections.emptyList();
    private Long id;
    private boolean checked;
    private boolean toAdmin;
    private boolean payed;
    private BigInteger priceAll;
    private User user;
    private boolean completed;

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isToAdmin() {
        return toAdmin;
    }

    public void setToAdmin(boolean toAdmin) {
        this.toAdmin = toAdmin;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public BigInteger getPriceAll() {
        return priceAll;
    }

    public void setPriceAll(BigInteger priceAll) {
        this.priceAll = priceAll;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public OrderDish() {
    }

    public OrderDish(List<Dish> dishes, Long id, boolean checked, boolean toAdmin, boolean payed, BigInteger priceAll, User user, boolean completed) {
        this.dishes = dishes;
        this.id = id;
        this.checked = checked;
        this.toAdmin = toAdmin;
        this.payed = payed;
        this.priceAll = priceAll;
        this.user = user;
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDish orderD = (OrderDish) o;
        return id == orderD.id &&
                Objects.equals(checked, orderD.checked) &&
                Objects.equals(toAdmin, orderD.toAdmin) &&
                Objects.equals(payed, orderD.payed) &&
                Objects.equals(priceAll, orderD.priceAll) &&
                Objects.equals(user, orderD.user) &&
                Objects.equals(completed, orderD.completed) &&
                Objects.equals(dishes, orderD.dishes);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checked, toAdmin, payed, priceAll, user, completed, dishes);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", checked='" + checked + '\'' +
                ", toAdmin='" + toAdmin + '\'' +
                ", payed='" + payed + '\'' +
                ", priceAll='" + priceAll + '\'' +
                ", user='" + user + '\'' +
                ", completed=" + completed +
                ", dishes=" + dishes +
                '}';
    }
}
