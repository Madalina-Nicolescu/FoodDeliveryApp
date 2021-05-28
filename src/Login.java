import service.DatabaseConnection;
import service.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame {
    public static JFrame frame;
    public JPanel panel1;
    public JLabel emailLabel;
    public JTextField emailField;
    public JButton LoginButton;
    private DatabaseConnection DB;
    private Program program;
    static GridBagLayout gridBag;
    static GridBagConstraints gbcons;

    static void adauga(Component comp, int x, int y, int w, int h) {
        gbcons.gridx = x;
        gbcons.gridy = y;
        gbcons.gridwidth = w;
        gbcons.gridheight = h;
        gridBag.setConstraints(comp, gbcons);
        frame.add(comp);
    }

    public Login() throws SQLException {
        frame = new JFrame("Log in");


        gridBag = new GridBagLayout();
        gbcons = new GridBagConstraints();
        gbcons.weightx = 1.0;
        gbcons.weighty = 1.0;
        gbcons.insets = new Insets(5, 5, 5, 5);
        frame.setLayout(gridBag);

        JLabel lblLogin = new JLabel("LOGIN", JLabel.CENTER);
        lblLogin.setFont(new Font(" Arial ", Font.BOLD, 24));
        gbcons.fill = GridBagConstraints.BOTH;
        adauga(lblLogin, 0, 0, 4, 2);

        emailLabel = new JLabel("Email address: ");
        gbcons.fill = GridBagConstraints.NONE;
        gbcons.anchor = GridBagConstraints.EAST;
        adauga(emailLabel, 0, 2, 1, 1);
        emailField = new JTextField("",30);
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        gbcons.anchor = GridBagConstraints.CENTER;
        adauga(emailField, 1, 2, 2, 1);
        LoginButton =new JButton("Log in");
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        adauga(LoginButton, 1, 4, 1, 1);

        frame.setSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        DB = DatabaseConnection.getInstance();
        program = Program.getInstance();
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    program.setCurrentUser(DB.getUserbyEmail(emailField.getText()));
                    frame.setVisible(false);
                    MainWindow main = new MainWindow();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

}