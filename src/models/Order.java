package models;

import java.util.List;

public class Order {
    private List<Item> menu;
    private double totalPrice;
    private Restaurant restaurant;

    public Order() {
    }

    public Order(List<Item> menu, int totalPrice) {
        this.menu = menu;
        this.totalPrice = totalPrice;


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
