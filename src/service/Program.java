package service;

import audit.Audit;
import audit.CSV;
import models.*;
import service.ServiceClassUser;

import java.io.FileReader;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Program {
    private static  Program instance= null;
    private static User currentUser;
    private static ServiceClassUser service;

    public static Program getInstance() throws SQLException {
        if (instance == null)
        {
            instance = new Program();
            currentUser = null;
            service = new ServiceClassUser();


        }
        return instance;
    }

    public static ServiceClassUser getService() {
        return service;
    }

    public static void setService(ServiceClassUser service) {
        Program.service = service;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static void main(String[] args) throws InterruptedException, SQLException {

        Scanner input = new Scanner(System.in);

        Audit audit = Audit.getInstance();
        Program program = Program.getInstance();


//        Item i1 = new Item("CheeseBurger", 18);
//        Item i2 = new Item("McChicken", 12);
//        Item i3 = new Item("BigMac", 12);
//        Item i4 = new Item("Fries", 5);
//        Item i5 = new Item("Coca Cola", 5);
//        Item i6 = new Item("Sprite", 5);
//        Item i7 = new Item("Pizza Quatro Stagioni", 30);
//        Item i8 = new Item("Pizza Margherita", 20);
//        Item i9 = new Item("Pasta Carbonara", 18);
//        Item i10 = new Item("Pasta Primavera", 16);
//        Item i11 = new Item("Caesar salad", 20);
//        Item i12 = new Item("Tuna salad", 18);
//        Item i13 = new Item("Fruit salad", 18);
//        Item i14 = new Item("Orange juice", 10);
//        Item i15 = new Item("Lemonade", 10);
//        Item i16 = new Item("Chicken sandwich",20);
//        Item i17 = new Item("Tuna sandwich", 23);
//        List<Item> food = new ArrayList<Item>();
//        food.add(i1);
//        food.add(i2);
//        food.add(i3);
//        food.add(i4);
//        List<Item> drinks = new ArrayList<Item>();
//        drinks.add(i5);
//        drinks.add(i6);
//        Menu m1 = new Menu(food,drinks);
//
//        food.clear();
//        drinks.clear();
//        food.add(i7);
//        food.add(i8);
//        food.add(i9);
//        food.add(i10);
//        drinks.add(i5);
//        drinks.add(i6);
//        Menu m2 = new Menu(food,drinks);
//
//        food.clear();
//        drinks.clear();
//        food.add(i11);
//        food.add(i12);
//        food.add(i13);
//        drinks.add(i14);
//        drinks.add(i15);
//        Menu m3 = new Menu(food,drinks);
//
//        food.clear();
//        drinks.clear();
//        food.add(i1);
//        food.add(i4);
//        food.add(i13);
//        drinks.add(i5);
//        drinks.add(i6);
//        Menu m4 = new Menu(food,drinks);
//
//        food.clear();
//        drinks.clear();
//        food.add(i17);
//        food.add(i16);
//        drinks.add(i5);
//        drinks.add(i6);
//        Menu m5 = new Menu(food,drinks);
//
//        food.clear();
//        drinks.clear();
//        food.add(i7);
//        food.add(i8);
//        food.add(i9);
//        drinks.add(i5);
//        drinks.add(i6);
//        drinks.add(i15);
//        Menu m6 = new Menu(food,drinks);
//
//        List<Menu> menus = new ArrayList<Menu>();
//        menus.add(m1);
//        menus.add(m2);
//        menus.add(m3);
//        menus.add(m4);
//        menus.add(m5);
//        menus.add(m6);

//        service.readRestaurants(menus);
       service.readVouchers();
        service.readDrivers();
        service.readManagers();

        service.welcome();

        TimeUnit.SECONDS.sleep(3);

        program.setCurrentUser(service.createAccount());


        int cont = 0;
        while(cont==0)
        {
            String option = service.chooseAction(input, program.getCurrentUser());

            switch (option)
            {
                case "1":
                    service.seeProfile(program.getCurrentUser());
                    audit.write("View Profile");
                    break;
                case "2":
                    service.updateProfile(program.getCurrentUser(), audit);
                    audit.write("Update Profile");
                    break;
                case "3":
                    audit.write("View Restaurants List");

                    service.displayRestaurants(DatabaseConnection.getInstance().readRestaurants(), program.getCurrentUser(), service.getVouchers(), service.getDrivers(), service.getManagers());
                    break;
                case "4":
                    audit.write("View Cart");
                    cont = program.getCurrentUser().getCart().displayCart(service.getOrder(),cont, service.getVouchers(), service.getDrivers(), service.getManagers(), audit);
                    break;
                case "5":
                    audit.write("Search Restaurant");
                    System.out.println("Search:");
                    String search = input.nextLine();
                    service.searchRestaurant(search, service.getRestaurants(), program.getCurrentUser(), service.getVouchers(), service.getDrivers(), service.getManagers());
                    break;
                default:
                    cont = 1;
                    break;

            }

            TimeUnit.SECONDS.sleep(5);
        }
    }
}
