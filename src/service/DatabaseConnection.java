package service;

import models.*;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class DatabaseConnection {

    private static  DatabaseConnection instance= null;
    private Connection connection;

    private DatabaseConnection() throws ClassNotFoundException, SQLException {
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fooddelivery", "admin", "password");
       }catch (SQLException | ClassNotFoundException throwables) {
           throwables.printStackTrace();
       }

    }

    public static DatabaseConnection getInstance()
    {
        if (instance == null)
        {
            try {
                instance = new DatabaseConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return instance;
    }


    public void close() throws Exception {
        connection.close();
    }

    //-----------------------------------CREATE OPERATIONS---------------------------------

    public User createUser( int id,String name, String address, String email, String phoneNumber, String role, int idCart ) throws SQLException {
        User user = new User(id,name,address, email, phoneNumber,role, idCart);
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO USER VALUES(?,?,?,?,?,?,?)");
        stmt.setString(1, String.valueOf(id));
        stmt.setString(2,name);
        stmt.setString(3,address);
        stmt.setString(4,email);
        stmt.setString(5,phoneNumber);
        stmt.setString(6,role);
        stmt.setString(7,String.valueOf(idCart));
        if(stmt.executeUpdate() == 1){
            return user;
        }
        return null;

    }

    public int getLastCartId() throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement("SELECT MAX(idcart) from cart");
        ResultSet results = stmt.executeQuery();
        int id = 0;
        while (results.next())
        {
            id = results.getInt(1);
        }
        return id;
    }

    public Cart createCart() throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO cart(price) VALUES(?)");
        Cart cart = new Cart(getLastCartId());
        stmt.setString(1,String.valueOf(0));
        if(stmt.executeUpdate() == 1){
            return cart;
        }
        return null;
    }

    public Boolean addItemToCart(int idcart, int iditem) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO cart_item(iditem,idcart) VALUES(?,?)");
        stmt.setString(1, String.valueOf(iditem));
        stmt.setString(2,String.valueOf(idcart));
        stmt = connection.prepareStatement("SELECT price FROM cart WHERE idcart =?");
        stmt.setString(1, String.valueOf(idcart));
        ResultSet results = stmt.executeQuery();
        while (results.next())
        {
            float price = results.getFloat(1);
            stmt = connection.prepareStatement("SELECT price FROM item WHERE iditem =?");
            stmt.setString(1, String.valueOf(iditem));
            ResultSet results2 = stmt.executeQuery();
            while (results2.next())
            {
                stmt = connection.prepareStatement("UPDATE cart SET price = ? WHERE idcart =?");
                stmt.setString(1, String.valueOf(price+results2.getFloat(1)));
                stmt.setString(2, String.valueOf(idcart));
                return stmt.executeUpdate() == 1;
            }

        }
        return stmt.executeUpdate()==1;
    }

    public Order createOrder(int id, int idUser, float price,int idRestaurant ) throws SQLException {
        Order order = new Order(id);
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO order VALUES(?,?,?,?)");
        stmt.setString(1, String.valueOf(id));
        stmt.setString(2,String.valueOf(idUser));
        stmt.setString(2,String.valueOf(price));
        stmt.setString(2,String.valueOf(idRestaurant));

        if(stmt.executeUpdate() == 1){
            return order;
        }
        return null;
    }
    public Item createItem(int id, String name, float price, String type) throws SQLException {
        Item item = new Item(id,name,price,type);
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO order VALUES(?,?,?,?)");
        stmt.setString(1, String.valueOf(id));
        stmt.setString(2,String.valueOf(name));
        stmt.setString(2,String.valueOf(price));
        stmt.setString(2,String.valueOf(type));

        if(stmt.executeUpdate() == 1){
            return item;
        }
        return null;
    }

    public Menu createMenu(int id) throws SQLException {
        Menu menu= new Menu(id);
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO menu VALUES(?)");
        stmt.setString(1, String.valueOf(id));
        if(stmt.executeUpdate() == 1){
            return menu;
        }
        return null;
    }

    public Restaurant createRestaurant(int id, String name, float rating, int noRating,String address, String program, int idMenu) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM menu WHERE idmenu = ?");
        stmt.setString(1,String.valueOf(idMenu));
        ResultSet result = stmt.executeQuery();
        Menu men = new Menu(idMenu);
        Restaurant restaurant = new Restaurant(id, name,rating, noRating, address, program);
        stmt = connection.prepareStatement("INSERT INTO USER VALUES(?,?,?,?,?,?,?)");
        stmt.setString(1, String.valueOf(id));
        stmt.setString(2,name);
        stmt.setString(3,String.valueOf(rating));
        stmt.setString(4,String.valueOf(noRating));
        stmt.setString(5,address);
        stmt.setString(6,program);
        stmt.setString(7,String.valueOf(idMenu));
        if(stmt.executeUpdate() == 1){
            return restaurant;
        }
        return null;
    }

    public Voucher createVoucher(int id, String code, int discount) throws SQLException {
        Voucher voucher = new Voucher(id,code,discount);
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO order VALUES(?,?,?)");
        stmt.setString(1, String.valueOf(id));
        stmt.setString(2,String.valueOf(code));
        stmt.setString(2,String.valueOf(discount));

        if(stmt.executeUpdate() == 1){
            return voucher;
        }
        return null;
    }




    //--------------------------------READ OPERATIONS---------------------------------------

    public TreeSet<Voucher> readVouchers() throws SQLException
    {
        TreeSet<Voucher> vouchers = new TreeSet<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM voucher");
        ResultSet results = statement.executeQuery();
        while (results.next()){
            Voucher voucher = new Voucher(results.getInt(1),results.getString(2),results.getInt(3));
            vouchers.add(voucher);
        }
        return vouchers;


    }

    public Item getItem(int id) throws SQLException
    {
        Item item = null;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE iditem = ?");
        statement.setString(1, String.valueOf(id));
        ResultSet results = statement.executeQuery();
        item = new Item(results.getInt(1),results.getString(2), results.getFloat(3), results.getString(4));
        return item;
    }

    public List<Item> getCartItems(int id) throws SQLException
    {
        List<Item> items= new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM cart_item WHERE idcart = ?");
        statement.setString(1, String.valueOf(id));
        ResultSet results = statement.executeQuery();
        while (results.next())
        {
            items.add(getItem(results.getInt(3)));
        }
        return items;
    }

    public List<Item> getOrderItems(int id) throws SQLException
    {
        List<Item> items= new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_item WHERE idorder = ?");
        statement.setString(1, String.valueOf(id));
        ResultSet results = statement.executeQuery();
        while (results.next())
        {
            items.add(getItem(results.getInt(3)));
        }
        return items;
    }

    public List<Item> getMenuItems(int id) throws SQLException
    {
        List<Item> items= new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM item_menu WHERE idmenu = ?");
        statement.setString(1, String.valueOf(id));
        ResultSet results = statement.executeQuery();
        while (results.next())
        {
            items.add(getItem(results.getInt(3)));
        }
        return items;
    }

    public Cart getDBCart(int id) throws SQLException {
        Cart cart = new Cart(id);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM cart WHERE idcart = ?");
        statement.setString(1, String.valueOf(cart.getId()));
        ResultSet results = statement.executeQuery();
        while (results.next())
        {
            cart = new Cart(id,getCartItems(id), results.getFloat(2));

        }
        return cart;
    }

    public User getUserbyEmail(String email) throws SQLException{
        User user = null;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
        statement.setString(1, email);
        ResultSet results = statement.executeQuery();
        while (results.next()){

            user = new User(results.getInt(1), results.getString(2),results.getString(3), email, results.getString(5), results.getString(6), results.getInt(7));
        }
        return user;
    }



    public List<Restaurant> readRestaurants() throws SQLException{
        List<Restaurant> restaurants = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM restaurants");
        ResultSet results = statement.executeQuery();
        while (results.next()){
            Restaurant restaurant = new Restaurant(results.getInt(1),results.getString(2),results.getFloat(3),results.getInt(4), results.getString(5),results.getString(6),results.getInt(7));
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    public List<Item> readItemsMenu(int idmenu) throws SQLException
    {
        List<Item> items = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM item JOIN item_menu ON(item.iditem = item_menu.iditem) JOIN menu ON (item_menu.idmenu = menu.idmenu) WHERE menu.idmenu = ?");
        statement.setString(1, String.valueOf(idmenu));
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            Item item = new Item(results.getInt(1),results.getString(2),results.getFloat(3),results.getString(4));
            items.add(item);
        }
        return items;
    }

    public Menu getMenu(int restaurant_id) throws SQLException
    {
        Menu menu = null;
        PreparedStatement statement = connection.prepareStatement("SELECT menu.idmenu FROM menu JOIN restaurants ON (menu.idmenu = restaurants.idmenu) WHERE idrestaurants = ? ");
        statement.setString(1, String.valueOf(restaurant_id));
        ResultSet results = statement.executeQuery();
        while (results.next())
        {
            List<Item> items = readItemsMenu(results.getInt(1));
            List<Item> food = new ArrayList<>();
            List<Item> drinks = new ArrayList<>();
            for(int i = 0; i<items.size(); i++)
            {
                if(items.get(i).getType().equals("food"))
                {
                    food.add(items.get(i));
                }
                else drinks.add(items.get(i));
            }
            menu = new Menu(results.getInt(1), food,drinks);
        }
        return menu;
    }

    public List<User> readUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user");
        ResultSet results = statement.executeQuery();
        while (results.next()){
            User user = new User(results.getInt(1),results.getString(2),results.getString(3),results.getString(4), results.getString(5),results.getString(6),results.getInt(7));
            users.add(user);
        }
        return users;
    }

    //--------------------------------UPDATE OPERATIONS-------------------------------------

    public boolean updateCart(Cart cart) throws Exception {
        PreparedStatement statement = connection.prepareStatement("UPDATE cart SET price = ? WHERE idcart = ?");
        statement.setString(1, String.valueOf(cart.getPrice()));
        statement.setString(2, String.valueOf(cart.getId()));
        return statement.executeUpdate() == 1;
    }

    public boolean updateItem(Item item) throws Exception {
        PreparedStatement statement = connection.prepareStatement("UPDATE item SET name = ?, price = ?, type = ? WHERE iditem = ?");
        statement.setString(1, item.getName());
        statement.setString(2, String.valueOf(item.getPrice()));
        statement.setString(3, item.getType());
        statement.setString(4, String.valueOf(item.getId()));
        return statement.executeUpdate() == 1;
    }

    public boolean updateOrder(Order order) throws Exception {
        PreparedStatement statement = connection.prepareStatement("UPDATE order SET iduser = ?, price = ?, idrestaurant = ? WHERE idorder = ?");
        statement.setString(1, String.valueOf(order.getUser().getId()));
        statement.setString(2, String.valueOf(order.getTotalPrice()));
        statement.setString(3, String.valueOf(order.getRestaurant().getId()));
        statement.setString(4, String.valueOf(order.getId()));
        return statement.executeUpdate() == 1;
    }

    public boolean updateUser(User user) throws Exception {
        PreparedStatement statement = connection.prepareStatement("UPDATE user SET name = ?, address = ?, email = ?, phoneNumber = ?, role = ? WHERE iduser = ?");
        statement.setString(1, user.getName());
        statement.setString(2, user.getAddress());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPhoneNumber());
        statement.setString(5, user.getRole());
        statement.setString(6, String.valueOf(user.getId()));
        return statement.executeUpdate() == 1;
    }
    public boolean updateRestaurant(Restaurant restaurant) throws Exception {
        PreparedStatement statement = connection.prepareStatement("UPDATE restaurants SET name = ?, rating = ?, noRating = ?, address = ?, program = ?, idmenu = ?  WHERE idrestaurants = ?");
        statement.setString(1, restaurant.getName());

        statement.setString(2, String.valueOf(restaurant.getRating()));
        statement.setString(3, String.valueOf(restaurant.getNoRating()));
        statement.setString(4, restaurant.getAddress());
        statement.setString(5, restaurant.getProgram());
        statement.setString(6,  String.valueOf(restaurant.getMenu().getId()));
        statement.setString(7, String.valueOf(restaurant.getId()));
        return statement.executeUpdate() == 1;
    }

    public boolean updateVoucher(Voucher voucher) throws Exception {
        PreparedStatement statement = connection.prepareStatement("UPDATE voucher SET code = ?, dicsount = ? WHERE idvoucher = ?");
        statement.setString(1, voucher.getCode());
        statement.setString(2, String.valueOf(voucher.getDiscount()));
        statement.setString(3, String.valueOf(voucher.getId()));
        return statement.executeUpdate() == 1;
    }

    //--------------------DELETE SECTION-----------------------------


    public boolean deleteCart(int id) throws Exception {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM cart WHERE id = ?");
        statement.setString(1, String.valueOf(id));
        return statement.executeUpdate() == 1;
    }

    public boolean deleteItem(int id) throws Exception {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM item WHERE id = ?");
        statement.setString(1, String.valueOf(id));
        return statement.executeUpdate() == 1;
    }

    public boolean deleteMenu(int id) throws Exception {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM menu WHERE id = ?");
        statement.setString(1, String.valueOf(id));
        return statement.executeUpdate() == 1;
    }
    public boolean deleteOrder(int id) throws Exception {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM order WHERE id = ?");
        statement.setString(1, String.valueOf(id));
        return statement.executeUpdate() == 1;
    }

    public boolean deleteRestaurant(int id) throws Exception {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM restaurants WHERE id = ?");
        statement.setString(1, String.valueOf(id));
        return statement.executeUpdate() == 1;
    }
    public boolean deleteUser(int id) throws Exception {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
        statement.setString(1, String.valueOf(id));
        return statement.executeUpdate() == 1;
    }
    public boolean deleteVoucher(int id) throws Exception {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM voucher WHERE id = ?");
        statement.setString(1, String.valueOf(id));
        return statement.executeUpdate() == 1;
    }
}
