package src;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LOGIN {
    private JTextField textField1;
    private JPasswordField passwordField1;
   private JComboBox comboBox1;
    public JPanel jPanel;
    private JButton ingresarButton;

    public LOGIN() {
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().isEmpty()||passwordField1.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"CAMPOS VACIOS. LLENE TODOS LOS DATOS","ERROR",1);
                }else{
                    String usuario = textField1.getText();
                    String password = passwordField1.getText();
                    String seleccion = (String) comboBox1.getSelectedItem();
                    System.out.println(seleccion);
                }


            }
        });
    }

}
