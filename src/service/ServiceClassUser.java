package service;

import models.*;

import java.util.*;

public class ServiceClassUser {
    private Order order;

    public ServiceClassUser() {
        this.order = new Order();
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

    public User createAccount()
    {
        System.out.println("\nFirst, let's create an account! ");
        User user = new User();
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

    public User updateProfile(User user)
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Do you want to edit your name? y/n");
        String option = input.nextLine();
        if(option.equalsIgnoreCase("y"))
        {
            System.out.println("What is yout name?");
            String name = input.nextLine();
            user.setName(name);
        }

        System.out.println("Do you want to edit your address? y/n");
        option = input.nextLine();
        if(option.equalsIgnoreCase("y")) {
            System.out.println("What is your address?");
            String address = input.nextLine();
            user.setAddress(address);
        }

        System.out.println("Do you want to edit your email address? y/n");
        option = input.nextLine();
        if(option.equalsIgnoreCase("y")) {
            System.out.println("What is your email?");
            String email = input.nextLine();
            user.setEmail(email);
        }

        System.out.println("Do you want to edit your phone number? y/n");
        option = input.nextLine();
        if(option.equalsIgnoreCase("y")) {
            System.out.println("What is your phone number?");
            String phoneNumber = input.nextLine();
            user.setPhoneNumber(phoneNumber);
        }

        return user;
    }

    public void displayRestaurants(List<Restaurant> restaurants, User user, TreeSet<Voucher> vouchers)
    {
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
        restaurants.get(i-1).displayRestaurant(user, this.order, vouchers);

    }



    public void searchRestaurant(String search, List<Restaurant> restaurants, User user, TreeSet<Voucher> vouchers)
    {
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

            searchResults.get(i-1).displayRestaurant(user, this.order, vouchers);

        }
        else
        {
            Restaurant restaurant = searchResults.get(0);
            restaurant.displayRestaurant(user, this.order, vouchers);
        }
    }
}
