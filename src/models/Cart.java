package models;

import service.ServiceClassUser;

import java.util.*;

public class Cart {
    private List<Item> items;
    private double price;

    public Cart() {
        this.items = new ArrayList<Item>();
        this.price = 0;
    }

    public Cart(List<Item> items, double price) {
        this.items = items;
        this.price = price;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int displayCart(Order order, int cont, TreeSet<Voucher> vouchers)
    {
        System.out.println("Items:");
        for(int i = 0; i < this.getItems().size(); i++)
        {
            System.out.println("\n");
            System.out.println((i+1) +". " + this.getItems().get(i).getName() +" -> " + this.getItems().get(i).getPrice());
        }
        System.out.println("\n-------------------");
        System.out.println("Total price: " + this.getPrice());

        if(this.items.size()>0)
        {
            System.out.println("\nFinish the order? y/n");
            Scanner input = new Scanner(System.in);
            String option = input.nextLine();
            if(option.equalsIgnoreCase("y"))
            {
                System.out.println("Do you want to apply a voucher? y/n");
                option = input.nextLine();
                if(option.equalsIgnoreCase("y"))
                {
                    int v = 0;
                    System.out.println("Introduce the voucher code:");
                    String code = input.nextLine();
                    for(Voucher voucher : vouchers)
                    {
                        if(voucher.getCode().equals(code))
                        {
                            double newPrice = this.getPrice()-this.getPrice()*voucher.getDiscount()/100;
                            this.setPrice(newPrice);
                            v++;
                            break;
                        }
                    }
                    if(v==0)
                    {
                        System.out.println("The voucher could not be found");
                    }
                }
                cont = 0;
                for (int i = 0; i < 50; ++i) System.out.println();
                order.setMenu(this.getItems());
                order.setTotalPrice(this.getPrice());
                System.out.println("Items:");
                for(int i = 0; i < this.getItems().size(); i++)
                {
                    System.out.println("\n");
                    System.out.println((i+1) +". " + this.getItems().get(i).getName() +" -> " + this.getItems().get(i).getPrice());
                }
                System.out.println("\n-------------------");
                System.out.println("Total price: " + this.getPrice());
                System.out.println("\n\n");
                System.out.println("Restaurant: " + order.getRestaurant().getName());

                this.price=0;
                this.items.clear();

            }

        }
    return cont;

    }
}
