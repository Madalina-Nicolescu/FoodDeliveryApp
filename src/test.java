import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test {
    public JButton button1;
    public JPanel panel1;

    public test() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Buna");
            }
        });
    }

    public static void main(String[] args) {
            JFrame frame = new JFrame();
            frame.setContentPane(new test().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
    }
}
