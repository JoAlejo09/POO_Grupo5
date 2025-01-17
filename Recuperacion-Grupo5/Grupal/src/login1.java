import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class login1 {
    private JTextField usuario1;
    private JPasswordField passwordField1;
    private JButton validarDatosButton;
    public JPanel mainPanel;
    private JLabel mensaje_validacion;
    private JComboBox tipo_de_rol;

    public login1() {
        validarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = usuario1.getText();
                String contraseña = new String(passwordField1.getPassword());
                String rolSeleccionado = (String) tipo_de_rol.getSelectedItem();

                // Llamada al método para validar usuario, contraseña y rol
                boolean esValido = validarUsuario(nombreUsuario, contraseña, rolSeleccionado);

                if (esValido) {
                    mensaje_validacion.setText("Acceso correcto");
                    abrirMenu(rolSeleccionado);  // Llamar a abrirMenu con el rol seleccionado
                } else {
                    mensaje_validacion.setText("Nombre de usuario, contraseña o rol incorrectos");
                }
            }
        });
    }

    // Método que valida el usuario, contraseña y rol
    private boolean validarUsuario(String nombreUsuario, String contraseña, String rol) {
        boolean esValido = false;

        try (Connection conn = DatabaseConnector.getConnection()) {
            // Consulta SQL para verificar nombre_usuario, contraseña y rol
            String sql = "SELECT 1 FROM usuarios WHERE nombre_usuario = ? AND password = ? AND rol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombreUsuario);
                stmt.setString(2, contraseña);
                stmt.setString(3, rol);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Si se encuentra una coincidencia, los datos son válidos
                        esValido = true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return esValido;
    }

    // Método que abre el menú principal dependiendo del rol seleccionado
    private void abrirMenu(String rol) {
        JFrame frame;
        if (rol.equals("usuario")) {
            frame = new JFrame("Formulario de Usuario");
            frame.setContentPane(new Formulario_de_usuarios().panel1);
        } else if (rol.equals("administrador")) {
            frame = new JFrame("Formulario de Administrador");
            frame.setContentPane(new Formulario_admin().panel1);
        } else {
            return;
        }

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setVisible(true);

        // Cerrar la ventana de login
        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        loginFrame.dispose();
    }
}
