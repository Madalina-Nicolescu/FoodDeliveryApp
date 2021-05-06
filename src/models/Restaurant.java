package models;

import audit.Audit;

import java.util.*;

public class Restaurant {
    private String name;
    private double rating;
    private int noRating;
    private String address;
    private String program;
    private Menu menu;

    public Restaurant(String name, double rating, int noRating, String address, String program) {
        this.name = name;
        this.rating = rating;
        this.noRating = noRating;
        this.address = address;
        this.program = program;
    }

    public Restaurant(String name, double rating,int noRating, String address, String program, Menu menu) {
        this.name = name;
        this.rating = rating;
        this.noRating = noRating;
        this.address = address;
        this.program = program;
        this.menu = menu;
    }

    public int getNoRating() {
        return noRating;
    }

    public void setNoRating(int noRating) {
        this.noRating = noRating;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void seeMenu()
    {
        System.out.println("\nFood Menu");
        for(int i = 0 ; i<this.menu.getFoodMenu().size(); i++)
        {
            System.out.println(this.menu.getFoodMenu().get(i).getName() + ".............." + this.menu.getFoodMenu().get(i).getPrice());
        }


        System.out.println("\nDrinks Menu");
        for(int i = 0 ; i<this.menu.getDrinksMenu().size(); i++)
        {
            System.out.println(this.menu.getDrinksMenu().get(i).getName() + ".............." + this.menu.getDrinksMenu().get(i).getPrice());
        }

        System.out.println("\n");
    }

    public void displayRestaurant(User user, Order order, TreeSet<Voucher> vouchers, Queue<Employee> drivers, List<Manager> managers, Audit audit)
    {
        System.out.println(this.name);
        System.out.println("Rating: " + this.rating + "/5");
        System.out.println("Address: " + this.address);
        System.out.println("Open: " + this.program);
        System.out.println("\n-------------------");
        System.out.println("1.See the menu");
        System.out.println("2.Give feedback");
        System.out.println("3.Add to favorites\n\n");
        System.out.println("Choose an option");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        switch (option)
        {
            case "1":
                this.seeMenu();
                audit.write("See " + this.name + " Menu");
                this.addToCart(user,menu,order,vouchers,drivers, managers, audit);
                break;
            case "2":
                this.giveFeedback();
                audit.write("Add Rating to " + this.name);
                break;
            case "3":
                this.addToFav(user);
                audit.write("Add " + this.name + "to Favourites");
                break;
            default:
                System.out.println("Back to main menu");
                break;
        }

    }

    public void addToFav(User user)
    {
        if(!user.getFavorites().contains(this))
        {
            user.getFavorites().add(this);
        }

    }

    public void giveFeedback()
    {
        double rating = this.rating;
        int noRatings = this.noRating;
        noRatings++;
        System.out.println("Give a rating");
        Scanner input = new Scanner(System.in);
        double r = Double.parseDouble((input.nextLine()));
        rating+=r;
        this.setRating(rating/noRatings);
        this.setNoRating(noRatings);

    }

    public void addToCart(User user, Menu menu, Order order, TreeSet<Voucher> vouchers, Queue<Employee> drivers, List<Manager> managers, Audit audit)
    {
        int cont = 1;
        while(cont == 1)
        {
            System.out.println("1.Add an item to cart");
            System.out.println("2.View cart");
            System.out.println("Introduce an option");
            Scanner input = new Scanner(System.in);
            String option = input.nextLine();
            switch (option)
            {
                case "1":
                    System.out.println("Choose item");
                    option = input.nextLine().toLowerCase(Locale.ROOT);
                    for(int i = 0; i < menu.getFoodMenu().size(); i++)
                    {
                        if(menu.getFoodMenu().get(i).getName().toLowerCase(Locale.ROOT).contains(option))
                        {
                            user.getCart().getItems().add(menu.getFoodMenu().get(i));
                            user.getCart().setPrice(user.getCart().getPrice()+menu.getFoodMenu().get(i).getPrice());
                        }
                    }
                    for(int i = 0; i < menu.getDrinksMenu().size(); i++)
                    {
                        if(menu.getDrinksMenu().get(i).getName().toLowerCase(Locale.ROOT).contains(option))
                        {
                            user.getCart().getItems().add(menu.getDrinksMenu().get(i));
                            user.getCart().setPrice(user.getCart().getPrice()+menu.getDrinksMenu().get(i).getPrice());
                        }
                    }
                    audit.write("Add Item to Order");
                    order.setRestaurant(this);
                    break;
                case "2":
                    cont = user.getCart().displayCart(order, cont, vouchers,drivers, managers, audit);
                    audit.write("View Cart");
                    break;
                default:
                    cont = 0;
                    break;
            }
        }

    }
}
