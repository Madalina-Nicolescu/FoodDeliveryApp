package service;

import models.*;
import service.ServiceClassUser;

import javax.xml.ws.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Program {
    public static void main(String[] args) throws InterruptedException {

        Item i1 = new Item("CheeseBurger", 18);
        Item i2 = new Item("McChicken", 12);
        Item i3 = new Item("BigMac", 12);
        Item i4 = new Item("Fries", 5);
        Item i5 = new Item("Coca Cola", 5);
        Item i6 = new Item("Sprite", 5);
        Item i7 = new Item("Pizza Quatro Stagioni", 30);
        Item i8 = new Item("Pizza Margherita", 20);
        Item i9 = new Item("Pasta Carbonara", 18);
        Item i10 = new Item("Pasta Primavera", 16);
        Item i11 = new Item("Caesar salad", 20);
        Item i12 = new Item("Tuna salad", 18);
        Item i13 = new Item("Fruit salad", 18);
        Item i14 = new Item("Orange juice", 10);
        Item i15 = new Item("Lemonade", 10);
        Item i16 = new Item("Chicken sandwich",20);
        Item i17 = new Item("Tuna sandwich", 23);
        List<Item> food = new ArrayList<Item>();
        food.add(i1);
        food.add(i2);
        food.add(i3);
        food.add(i4);
        List<Item> drinks = new ArrayList<Item>();
        drinks.add(i5);
        drinks.add(i6);
        Menu m1 = new Menu(food,drinks);

        food.clear();
        drinks.clear();
        food.add(i7);
        food.add(i8);
        food.add(i9);
        food.add(i10);
        drinks.add(i5);
        drinks.add(i6);
        Menu m2 = new Menu(food,drinks);


        food.clear();
        drinks.clear();
        food.add(i11);
        food.add(i12);
        food.add(i13);
        drinks.add(i14);
        drinks.add(i15);
        Menu m3 = new Menu(food,drinks);

        food.clear();
        drinks.clear();
        food.add(i1);
        food.add(i4);
        food.add(i13);
        drinks.add(i5);
        drinks.add(i6);
        Menu m4 = new Menu(food,drinks);

        food.clear();
        drinks.clear();
        food.add(i17);
        food.add(i16);
        drinks.add(i5);
        drinks.add(i6);
        Menu m5 = new Menu(food,drinks);

        food.clear();
        drinks.clear();
        food.add(i7);
        food.add(i8);
        food.add(i9);
        drinks.add(i5);
        drinks.add(i6);
        drinks.add(i15);
        Menu m6 = new Menu(food,drinks);


        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        Restaurant r1 = new Restaurant("McDonald's", 4.0, 100,"Constantin Brancoveanu", "10:00-21:00",m1);
        Restaurant r2 = new Restaurant("Jerry's Pizza", 4.7, 80,"Drumul Binelui", "10:00-23:00", m2);
        Restaurant r3 = new Restaurant("Salad Box", 4.5, 20,"Calea Vacaresti", "10:00-20:00", m3);
        Restaurant r4 = new Restaurant("Burger King", 4.6,90, "Nitu Vasile", "09:00-21:00",m4);
        Restaurant r5 = new Restaurant("Subway", 4.6, 120,"Calea Vacaresti", "10:00-20:00",m5);
        Restaurant r6 = new Restaurant("Pizza Hut", 4.4, 95, "Piata Romana", "10:00-21:00",m6);

        restaurants.add(r1);
        restaurants.add(r2);
        restaurants.add(r3);
        restaurants.add(r4);
        restaurants.add(r5);
        restaurants.add(r6);

        class cmpVouchers implements Comparator<Voucher> {
            @Override
            public int compare(Voucher x, Voucher y) {
                return y.getDiscount() - x.getDiscount();
            }
        }

        TreeSet<Voucher> vouchers = new TreeSet<Voucher>(new cmpVouchers());
        Voucher v1 = new Voucher("disc15", 15);
        Voucher v2 = new Voucher("disc20", 20);
        Voucher v3 = new Voucher("disc30", 30);

        vouchers.add(v1);
        vouchers.add(v2);
        vouchers.add(v3);

        Scanner input = new Scanner(System.in);
        ServiceClassUser service = new ServiceClassUser();

        service.welcome();

        TimeUnit.SECONDS.sleep(3);

        User user;
        user = service.createAccount();


        int cont = 0;
        while(cont==0)
        {
            String option = service.chooseAction(input,user);

            switch (option)
            {
                case "1":
                    service.seeProfile(user);
                    break;
                case "2":
                    service.updateProfile(user);
                    break;
                case "3":
                    service.displayRestaurants(restaurants, user, vouchers);
                    break;
                case "4":
                    cont = user.getCart().displayCart(service.getOrder(),cont, vouchers);
                    break;
                case "5":
                    System.out.println("Search:");
                    String search = input.nextLine();
                    service.searchRestaurant(search, restaurants, user, vouchers);
                    break;
                default:
                    cont = 1;
                    break;

            }

            TimeUnit.SECONDS.sleep(5);
        }
    }
}
