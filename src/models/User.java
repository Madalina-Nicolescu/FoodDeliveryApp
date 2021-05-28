package models;

import service.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private static int crt;
    private int id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private Cart cart;
    private String role;
    private List<Restaurant> favorites;

    public User() {
        crt++;
        this.favorites = new ArrayList<Restaurant>();
    }

    public User(int id, String name, String address, String email, String phoneNumber, String role, int idCart) throws SQLException {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.favorites = new ArrayList<Restaurant>();
        this.cart= DatabaseConnection.getInstance().getDBCart(id);
        crt++;
    }

    public static int getCrt() {
        return crt;
    }

    public static void setCrt(int crt) {
        User.crt = crt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Restaurant> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Restaurant> favorites) {
        this.favorites = favorites;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public User initialize()
    {
        Scanner input = new Scanner(System.in);

        System.out.println("What is yout name?");
        String name = input.nextLine();
        this.setName(name);

        System.out.println("What is your address?");
        String address = input.nextLine();
        this.setAddress(address);

        System.out.println("What is your email?");
        String email = input.nextLine();
        this.setEmail(email);

        System.out.println("What is your phone number?");
        String phoneNumber = input.nextLine();
        this.setPhoneNumber(phoneNumber);

        return this;
    }
}
