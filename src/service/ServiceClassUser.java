package service;

import audit.Audit;
import audit.CSV;
import models.*;

import java.sql.SQLException;
import java.util.*;
class cmpVouchers implements Comparator<Voucher> {
    @Override
    public int compare(Voucher x, Voucher y) {
        return y.getDiscount() - x.getDiscount();
    }
}

public class ServiceClassUser {
    private Order order;
    private TreeSet<Voucher> vouchers;
    private Queue<Driver> drivers;
    private List<Manager> managers;
    private List<Restaurant> restaurants;
    private static CSV csv = CSV.getInstance();

    public ServiceClassUser() throws SQLException {
        order = new Order(Order.getCrt());
        vouchers = new TreeSet<Voucher>(new cmpVouchers());
        drivers = new LinkedList<Driver>();
        managers = new ArrayList<Manager>();
        restaurants = DatabaseConnection.getInstance().readRestaurants();

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void welcome()
    {
        System.out.println("Welcome! What a beautiful day to eat your favorite food! :)");
    }

    public void readVouchers()
    {
        for(String line : csv.read("vouchers.csv"))
        {
            String[] voucher = line.split(", ");
            String code = voucher[0];
            int discount = Integer.parseInt(voucher[1]);
            Voucher v = new Voucher(code,discount);
            vouchers.add(v);
        }

    }


    public void readDrivers()
    {
        for(String line : csv.read("drivers.csv"))
        {
            String[] driver = line.split(", ");

            String name = driver[0];
            int salary = Integer.parseInt(driver[1]);
            String phone = driver[2];
            String carModel = driver[3];
            String licensePlate = driver[4];

           Driver d = new Driver(name,salary,phone,carModel,licensePlate);
           drivers.add(d);
        }

    }

    public String showDrivers()
    {
        String inf = "";
        for(Driver driver:drivers)
        {
            inf+="\n\n\t\tName: " + driver.getName();
            inf+="\n\t\tSalary: " + String.valueOf(driver.getSalary());
            inf+="\n\t\tPhone Number: " + driver.getPhone();
            inf+= "\n\t\tCar: " + driver.getCarModel();
            inf+="\n\t\tLicense Plate: " + driver.getLicensePlate();
            inf+="\n\t------------------------------------------------------------------------";
        }


        return inf;

    }

    public void readManagers()
    {
        for(String line : csv.read("managers.csv"))
        {
            String[] manager = line.split(", ");

            String name = manager[0];
            int salary = Integer.parseInt(manager[1]);
            String phone = manager[2];
            String restName = manager[3];
            Restaurant r = new Restaurant();
            for(Restaurant restaurant : restaurants)
            {
                if(restaurant.getName().equalsIgnoreCase(restName))
                {
                    r = new Restaurant(restaurant.getName(),restaurant.getRating(), restaurant.getNoRating(),restaurant.getAddress(),restaurant.getProgram());
                }
            }

            Manager m = new Manager(name,salary,phone,r);
            managers.add(m);
        }

    }
/*
    public void readRestaurants(List<Menu> menus)
    {
        int i = 0;
        for(String line : csv.read("restaurants.csv"))
        {
            String[] restaurant = line.split(", ");

            String name=restaurant[0];
            double rating = Double.parseDouble(restaurant[1]);
            int noRating = Integer.parseInt(restaurant[2]);
            String address = restaurant[3];
            String program = restaurant[4];


            Restaurant r = new Restaurant(name,rating,noRating,address,program,menus.get(i));
            i++;
            restaurants.add(r);
        }

    }*/

    public TreeSet<Voucher> getVouchers() {

        return vouchers;
    }

    public void setVouchers(TreeSet<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public Queue<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Queue<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public User createAccount()
    {

        System.out.println("\nFirst, let's create an account! ");
        User user = new User();
        Cart cart= new Cart(Cart.getCrt());
        user.setCart(cart);
        user.initialize();
        return user;
    }

    public String chooseAction(Scanner input,User user)
    {
        System.out.println("\nGreat! What you want to do next, " + user.getName() + "?");
        System.out.println("1.See my profile");
        System.out.println("2.Update my profile information");
        System.out.println("3.See all the restaurants");
        System.out.println("4.Go to my cart");
        System.out.println("5.Search for a restaurant");

        System.out.println("\nIntroduce an option:");
        String option;
        option = input.nextLine();

        return option;

    }

    public void seeProfile(User user)
    {
        System.out.println("Name: " + user.getName());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone: " + user.getPhoneNumber());

        if(user.getFavorites().size()!=0)
        {
            System.out.println("Your Favorite Restaurants: ");
            for(int i = 0 ; i < user.getFavorites().size(); i++)
            {
                System.out.println((i+1) + ". " + user.getFavorites().get(i).getName());
            }

        }

    }

    public User updateProfile(User user, Audit audit)
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Do you want to edit your name? y/n");
        String option = input.nextLine();
        if(option.equalsIgnoreCase("y"))
        {
            System.out.println("What is yout name?");
            String name = input.nextLine();
            user.setName(name);
            audit.write("Edit User Name");
        }

        System.out.println("Do you want to edit your address? y/n");
        option = input.nextLine();
        if(option.equalsIgnoreCase("y")) {
            System.out.println("What is your address?");
            String address = input.nextLine();
            user.setAddress(address);
            audit.write("Edit User Address");
        }

        System.out.println("Do you want to edit your email address? y/n");
        option = input.nextLine();
        if(option.equalsIgnoreCase("y")) {
            System.out.println("What is your email?");
            String email = input.nextLine();
            user.setEmail(email);
            audit.write("Edit User Email");
        }

        System.out.println("Do you want to edit your phone number? y/n");
        option = input.nextLine();
        if(option.equalsIgnoreCase("y")) {
            System.out.println("What is your phone number?");
            String phoneNumber = input.nextLine();
            user.setPhoneNumber(phoneNumber);
            audit.write("Edit User Phone Number");
        }

        return user;
    }

    public void displayRestaurants(List<Restaurant> restaurants, User user, TreeSet<Voucher> vouchers, Queue<Driver> drivers, List<Manager> managers) throws SQLException {
        for(int i = 0; i< restaurants.size(); i++)
        {
            System.out.println("\n-------------------");
            System.out.println((i+1) +". "+ restaurants.get(i).getName());
            System.out.println("Rating: " + restaurants.get(i).getRating() + "/5");
            System.out.println("Address: " + restaurants.get(i).getAddress());
            System.out.println("Open: " + restaurants.get(i).getProgram());
        }
        System.out.println("Choose a restaurant: ");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        int i =  Integer.parseInt(option);
        restaurants.get(i-1).displayRestaurant(user, this.order, vouchers,drivers, managers,Audit.getInstance());

    }



    public void searchRestaurant(String search, List<Restaurant> restaurants, User user, TreeSet<Voucher> vouchers, Queue<Driver> drivers, List<Manager> managers) throws SQLException {
        int j = 0;
        List<Restaurant> searchResults = new ArrayList<Restaurant>();
        for(int i = 0; i < restaurants.size(); i++)
        {

            if(restaurants.get(i).getName().toLowerCase().contains(search.toLowerCase()))
            {
                System.out.println((++j) +". " + restaurants.get(i).getName());
                System.out.println("Rating: " + restaurants.get(i).getRating() + "/5");
                System.out.println("Address: " + restaurants.get(i).getAddress());
                System.out.println("Open: " + restaurants.get(i).getProgram());
                System.out.println("\n-------------------");

                searchResults.add(restaurants.get(i));

            }

        }
        if(j==0)
        {
            System.out.println("This restaurant could not be found :(");
        }
        else if (j>1)
        {
            System.out.println("Choose one restaurant:");
            Scanner input = new Scanner(System.in);
            String option = input.nextLine();
            int i = Integer.parseInt(option);

            searchResults.get(i-1).displayRestaurant(user, this.order, vouchers,drivers, managers,Audit.getInstance());

        }
        else
        {
            Restaurant restaurant = searchResults.get(0);
            restaurant.displayRestaurant(user, this.order, vouchers, drivers,managers, Audit.getInstance());
        }
    }
}
