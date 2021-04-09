package models;

public class Employee {
    protected String name;
    protected Restaurant restaurant;
    protected int salary;


    public Employee(String name, Restaurant restaurant, int salary) {
        this.name = name;
        this.restaurant = restaurant;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
