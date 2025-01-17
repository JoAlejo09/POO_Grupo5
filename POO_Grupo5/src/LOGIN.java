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

    //Creación del Constructor
    public LOGIN() {
        comboBox1.addItem("Usuario");
        comboBox1.addItem("Administrador");

        // Accion del boton ingresar
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().isEmpty() || passwordField1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Campos vacios, porfavor llene todos los datos", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    String usuario = textField1.getText();
                    String password = new String(passwordField1.getPassword());
                    String rolSeleccionado = (String) comboBox1.getSelectedItem();

                    if (validarCredenciales(usuario, password, rolSeleccionado)) {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso. Rol: " + rolSeleccionado);
                        abrirVentanaSegunRol(rolSeleccionado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario, contraseña o rol incorrectos.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    // Creamos este método para la validacion de las credenciales
    private boolean validarCredenciales(String usuario, String password, String rol) {
        String consulta = "Select Sount(*) As total From usuarios Where nombre_usuario = ? And password = ? And rol = ?";
        try (Connection conexion = ConectorBaseDatos.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, usuario);
            stmt.setString(2, password);
            stmt.setString(3, rol);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0; // Si hay resultados, las credenciales seran correctas
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false; // en el caso de que no haya resultados, las credenciales seran incorrectas
    }

    // Para que abra una ventana segun el rol
    private void abrirVentanaSegunRol(String rol) {
        if (rol.equalsIgnoreCase("Usuario")) {
            JOptionPane.showMessageDialog(null, "Abriendo ventana para Usuario...");
            // abrirá la ventana específica para el Usuario


        } else if (rol.equalsIgnoreCase("Administrador")) {
            JOptionPane.showMessageDialog(null, "Abriendo ventana para Administrador...");
            // abrirá la ventana específica para el Administrador
        }
    }
}
