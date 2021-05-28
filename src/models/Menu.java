package models;

import java.util.List;

public class Menu {
    private int id;
    private List<Item> foodMenu;
    private List<Item> drinksMenu;

    public Menu(int id) {
        this.id = id;
    }

    public Menu(int id, List<Item> foodMenu, List<Item> drinksMenu) {
        this.id = id;
        this.foodMenu = foodMenu;
        this.drinksMenu = drinksMenu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(List<Item> foodMenu) {
        this.foodMenu = foodMenu;
    }

    public List<Item> getDrinksMenu() {
        return drinksMenu;
    }

    public void setDrinksMenu(List<Item> drinksMenu) {
        this.drinksMenu = drinksMenu;
    }
}
