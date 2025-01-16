package src;

import com.mysql.cj.log.Log;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Formulario Usuarios");
        frame.setContentPane(new LOGIN().jPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 350);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
    }

}
