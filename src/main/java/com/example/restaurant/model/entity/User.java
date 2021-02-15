package com.example.restaurant.model.entity;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private BigDecimal balance;
    private Set<Role> authorities = new HashSet<>();
    private Product prodLikeNow;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Product getProdLikeNow() {
        return prodLikeNow;
    }

    public void setProdLikeNow(Product prodLikeNow) {
        this.prodLikeNow = prodLikeNow;
    }

    public User(String firstName,
                String lastName,
                String username,
                String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                username.equals(user.username) &&
                password.equals(user.password) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(authorities, user.authorities) &&
                Objects.equals(prodLikeNow, user.prodLikeNow);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, password, authorities, prodLikeNow);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", balance='" + balance + '\'' +
                ", authorities=" + authorities +
                ", prodLikeNow=" + prodLikeNow +
                '}';
    }


    public boolean hasAuthority(Role authority) {
        return authorities.contains(authority);
    }

    public void addAuthority(Role authority) {
        authorities.add(authority);
    }
}
