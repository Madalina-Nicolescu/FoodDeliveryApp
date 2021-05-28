import service.DatabaseConnection;
import service.Program;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class InitialPage {
    private JFrame frame;
    //private Program program;
    private JPanel panel;


    public InitialPage()
    {
        frame = new JFrame();
        frame.setSize(new Dimension(400,400));
        JButton loginButton = new JButton("Log in");
        frame.setLayout(new GridLayout(3, 1));
        loginButton.setSize(new Dimension(40,40));
        JButton registerButton = new JButton("Register");
        registerButton.setSize(new Dimension(40,40));
        DatabaseConnection DB = DatabaseConnection.getInstance();
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        JTextArea welcomeTextArea = new JTextArea("Welcome! What a beautiful day to eat your favorite food! :)");
        welcomeTextArea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        welcomeTextArea.setFont(new Font("Arial Black", Font.BOLD, 20));

        frame.add(welcomeTextArea);
        frame.add(registerButton);
        frame.add(loginButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Login login = new Login();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    RegisterPage registerPage = new RegisterPage();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {

        InitialPage i = new InitialPage();


    }


}
