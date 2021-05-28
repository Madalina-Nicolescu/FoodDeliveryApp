import models.Cart;
import models.User;
import service.DatabaseConnection;
import service.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterPage extends JFrame{
    private JFrame frame;
    private DatabaseConnection DB;
    private Program program;
    public JTextField nameField;
    public JButton Register;
    public JPanel panel;
    public JTextField emailField;
    public JTextField addressField;
    public JTextField phoneField;
    public JLabel nameLabel;
    public JLabel emailLabel;
    public JLabel addressLabel;
    public JLabel phoneLabel;





    public RegisterPage() throws SQLException {
        program = Program.getInstance();
        DB = DatabaseConnection.getInstance();
        frame = new JFrame("Register");
        frame.setLayout(new GridLayout(5, 2));
        nameLabel = new JLabel("Name: ");
        emailLabel = new JLabel("Email address: ");
        addressLabel = new JLabel("Address: ");
        phoneLabel = new JLabel("Phone Number: ");
        nameField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextField();
        phoneField = new JTextField();
        Register = new JButton("Register");


        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(addressLabel);
        frame.add(addressField);
        frame.add(phoneLabel);
        frame.add(phoneField);
        frame.add(Register);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cart cart = DB.createCart();
                    System.out.println(cart.getId());
                    program.setCurrentUser(DB.createUser(User.getCrt(),nameField.getText(),addressField.getText(),emailField.getText(),phoneField.getText(),"user",cart.getId()));
                    frame.setVisible(false);
                    MainWindow main = new MainWindow();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }



}
