package models;

import java.util.List;

public class Menu {
    private List<Item> foodMenu;
    private List<Item> drinksMenu;

    public Menu(List<Item> foodMenu, List<Item> drinksMenu) {
        this.foodMenu = foodMenu;
        this.drinksMenu = drinksMenu;
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
