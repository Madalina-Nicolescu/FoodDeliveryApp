package models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int crt=0;
    private int id;
    private User user;
    private List<Item> menu;
    private double totalPrice;
    private Restaurant restaurant;


    public Order(int id) {
        this.crt++;
        this.id = id;
        this.menu = new ArrayList<>();
        this.totalPrice = 0;
    }

    public Order(int id, List<Item> menu, int totalPrice) {
        this.crt++;
        this.id = id;
        this.menu = menu;
        this.totalPrice = totalPrice;


    }

    public static int getCrt() {
        return crt;
    }

    public static void setCrt(int crt) {
        Order.crt = crt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getMenu() {
        return menu;
    }

    public void setMenu(List<Item> menu) {
        this.menu = menu;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
