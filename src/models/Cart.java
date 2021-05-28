package models;




import audit.Audit;

import java.net.PortUnreachableException;
import java.util.*;

public class Cart {
    private static int crt;
    private int id;
    private List<Item> items;
    private double price;

    public Cart(int id) {
        this.id = id;
        this.items = new ArrayList<>();
        this.price = 0;
        crt++;
    }

    public Cart(int id, List<Item> items, double price) {
        this.id =id;
        this.items = items;
        this.price = price;
        crt++;
    }

    public static int getCrt() {
        return crt;
    }

    public static void setCrt(int crt) {
        Cart.crt = crt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String showCart()
    {
        String inf = "";
        for(int i = 0; i < this.getItems().size(); i++)
        {
            inf+="\n";
            inf= inf+ String.valueOf(i+1) +". " + this.getItems().get(i).getName() +" -> " + this.getItems().get(i).getPrice();
        }
        inf+=("\n\n-------------------");
        inf=inf+ "Total price: " + this.getPrice();
        return inf;
    }

    public int displayCart(Order order, int cont, TreeSet<Voucher> vouchers, Queue<Driver> drivers, List<Manager> managers, Audit audit)
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
                            audit.write("Apply Voucher");
                            break;

                        }
                    }
                    if(v==0)
                    {
                        System.out.println("The voucher could not be found");
                    }
                }
                Manager man = new Manager("",0,"");
                for(Manager m : managers)
                {
                    if(m.getRestaurant().getName().equals(order.getRestaurant().getName()))
                    {
                        man = m;
                    }
                }
                cont = 0;
                for (int i = 0; i < 50; ++i) System.out.println();
                order.setMenu(this.getItems());
                order.setTotalPrice(this.getPrice());
                System.out.println("Items:");
                for(int i = 0; i < this.getItems().size(); i++)
                {
                    System.out.println((i+1) +". " + this.getItems().get(i).getName() +" -> " + this.getItems().get(i).getPrice());
                }
                System.out.println("\n-------------------");
                System.out.println("Total price: " + this.getPrice());
                System.out.println("\n");
                System.out.println("Restaurant: " + order.getRestaurant().getName() + "; Manager: " + man.getName() );
                System.out.println("\nYour order will be delivered by " + drivers.peek().getName());
                System.out.println("You can contact him at " + drivers.peek().getPhone());
                Driver d = drivers.remove();
                drivers.add(d);
                audit.write("Finish Order");
                this.price=0;
                this.items.clear();

            }

        }
    return cont;

    }
}
