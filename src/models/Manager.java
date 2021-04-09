package models;

public class Manager extends Employee{
    private Restaurant restaurant;
    public Manager(String name, int salary,String phone, Restaurant restaurant) {
        super(name, salary, phone);
        this.restaurant = restaurant;
    }

    public Manager(String name, int salary, String phone) {
        super(name, salary, phone);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
