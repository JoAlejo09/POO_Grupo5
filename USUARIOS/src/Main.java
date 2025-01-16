import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Formulario Usuarios");
        frame.setContentPane(new Formulario_de_usuarios().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
