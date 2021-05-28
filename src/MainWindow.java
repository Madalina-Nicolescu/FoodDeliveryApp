import models.*;
import models.Menu;
import service.DatabaseConnection;
import service.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.TextListener;
import java.sql.SQLException;
import java.util.List;

public class MainWindow {
    private JFrame frame;
    private JPanel panel;
    private Program program;
    private DatabaseConnection DB;

    static void add(JTabbedPane tabbedPane, String label, int mnemonic) {
        int count = tabbedPane.getTabCount();
        JButton button = new JButton(label);
        tabbedPane.addTab(label, new ImageIcon("a.gif"), button, label);
        tabbedPane.setMnemonicAt(count, mnemonic);

    }

    public JScrollPane showRestaurants() throws SQLException {
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea("\t\t\tRESTAURANTS\n");
        textArea.setFont(new Font("Serif", Font.BOLD, 20));
        panel.add(textArea);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        List<Restaurant> restaurants = this.DB.readRestaurants();
        int i = 0;
        for(i = 0; i<restaurants.size(); i++)
        {
            JTextArea text = new JTextArea(restaurants.get(i).showRestaurant());
            text.setFont(new Font("Serif", Font.BOLD, 16));
            panel.add(text);
            JButton button = new JButton("See menu");
            button.setBackground(new Color(189, 242, 221));
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("Menu");

                    JPanel panel1 = new JPanel();
                    panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

                    String inf = "\n\t        FOOD\n";
                    JTextArea textArea1 = new JTextArea(inf);
                    panel1.add(textArea1);
                    Menu menu = null;
                    try {

                        menu = DatabaseConnection.getInstance().getMenu(restaurants.get(finalI).getId());

                        for(int i = 0; i <menu.getFoodMenu().size();i++)
                        {
                            inf = "";
                            inf = inf +"\n\t" + menu.getFoodMenu().get(i).getName() + ".............." + menu.getFoodMenu().get(i).getPrice();
                            inf+="\t\n";
                            textArea1=new JTextArea(inf);
                            JButton button1 = new JButton("Add to cart");
                            Menu finalMenu = menu;
                            int finalI1 = i;
                            button1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        DB.addItemToCart(program.getCurrentUser().getCart().getId(), finalMenu.getFoodMenu().get(finalI1).getId());
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                }
                            });

                            panel1.add(textArea1);
                            panel1.add(button1);
                        }


                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    JScrollPane scrollPane = new JScrollPane(panel1);

                    JPanel panel2 = new JPanel();
                    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
                    inf = "\n\t        DRINKS\n";
                    textArea1 = new JTextArea(inf);
                    panel2.add(textArea1);


                    for(int i = 0; i <menu.getDrinksMenu().size();i++)
                    {
                        inf = "";
                        inf = inf +"\n\t" + menu.getDrinksMenu().get(i).getName() + ".............." + menu.getDrinksMenu().get(i).getPrice();
                        inf+="\t\n";
                        textArea1=new JTextArea(inf);
                        JButton button1 = new JButton("Add to cart");

                        panel2.add(textArea1);
                        panel2.add(button1);
                    }


                    JSplitPane sl = new JSplitPane(SwingConstants.VERTICAL, scrollPane, panel2);

                    sl.setOrientation(SwingConstants.VERTICAL);

                    // add panel
                    frame.add(sl);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frame.setSize(new Dimension(600, 600));
                    frame.setVisible(true);
                }
            });
            panel.add(button);
        }
        JScrollPane scrollPane = new JScrollPane(panel);

        return scrollPane;
    }


    public JScrollPane showCart() throws SQLException {
        JScrollPane main_panel = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Cart cart = DB.getDBCart(program.getCurrentUser().getCart().getId());
        JTextArea textArea = new JTextArea(cart.showCart());
        panel.add(textArea);
        main_panel.add(panel);
        return main_panel;
    }

    public JScrollPane showDrivers() throws  SQLException
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        program.getService().readDrivers();
        JTextArea textArea = new JTextArea(program.getService().showDrivers());
        textArea.setFont(new Font("Serif", Font.BOLD, 16));
        panel.add(textArea);
        panel.setVisible(true);
        JScrollPane main_panel = new JScrollPane(panel);
        return main_panel;
    }

    public JScrollPane showManagers() throws  SQLException
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        program.getService().readManagers();

        List<Manager> managers = program.getService().getManagers();
        for(Manager manager:managers)
        {
            String inf = "";
            inf+="\n\n\t\tName: " + manager.getName();
            inf+="\n\t\tSalary: " + String.valueOf(manager.getSalary());
            inf+="\n\t\tPhone Number: " + manager.getPhone();
            //inf+= "\n\t\tManager at: " + manager.getRestaurant().getName();
            inf+="\n\t------------------------------------------------------------------------";
            JTextArea textArea = new JTextArea(inf);
            textArea.setFont(new Font("Serif", Font.BOLD, 16));
            panel.add(textArea);
        }

        panel.setVisible(true);
        JScrollPane main_panel = new JScrollPane(panel);
        return main_panel;
    }


    public JScrollPane showUsers() throws SQLException
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        List<User> users = DB.readUsers();
        for(User u : users)
        {
            String inf = "";
            inf = inf + "\n\t\tName: " + u.getName();
            inf = inf+"\n\t\tEmail: " + u.getEmail();
            inf = inf+"\n\t\tPhone Number: " + u.getPhoneNumber();
            inf = inf+"\n\t\tAddress: " + u.getAddress();
            inf = inf+"\n\t\tRole: " + u.getRole();
            inf+="\n\t-----------------------------------------------------";
            JTextArea textArea = new JTextArea(inf);
            textArea.setFont(new Font("Serif", Font.BOLD, 16));
            panel.add(textArea);
        }
        JScrollPane main_panel = new JScrollPane(panel);
        return main_panel;


    }

    public MainWindow() throws SQLException {
        program = Program.getInstance();
        DB = DatabaseConnection.getInstance();
        frame = new JFrame("Tabbed Pane Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(189, 242, 221));

        if(program.getCurrentUser().getRole().equals("user"))
        {

            int count = tabbedPane.getTabCount();
            tabbedPane.addTab("Restaurants",  showRestaurants());
            tabbedPane.setMnemonicAt(count, KeyEvent.VK_1);

            count = tabbedPane.getTabCount();
            tabbedPane.addTab("Cart",  showCart());
            tabbedPane.setMnemonicAt(count, KeyEvent.VK_2);
        }
        else
        {
            int count = tabbedPane.getTabCount();
            tabbedPane.addTab("Restaurants",  showRestaurants());
            tabbedPane.setMnemonicAt(count, KeyEvent.VK_1);

            count = tabbedPane.getTabCount();
            tabbedPane.addTab("Users",  showUsers());
            tabbedPane.setMnemonicAt(count, KeyEvent.VK_2);

            count = tabbedPane.getTabCount();
            tabbedPane.addTab("Drivers", showDrivers());
            tabbedPane.setMnemonicAt(count, KeyEvent.VK_3);

            count = tabbedPane.getTabCount();
            tabbedPane.addTab("Managers",  showManagers());
            tabbedPane.setMnemonicAt(count, KeyEvent.VK_4);
        }


        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

}
