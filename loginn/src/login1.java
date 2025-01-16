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
        // Listener para el botón de validación
        validarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = usuario1.getText();
                String contraseña = new String(passwordField1.getPassword());

                // Llamada a un método que valida usuario y contraseña, y devuelve el rol
                String rol = validarUsuarioPorCorreo(correo, contraseña);

                if (rol != null) {
                    mensaje_validacion.setText("Acceso correcto");
                    tipo_de_rol.setSelectedItem(rol);  // Establecer el rol en el JComboBox
                    abrirMenu(rol);  // Llamar a abrirMenu con el rol del usuario
                } else {
                    mensaje_validacion.setText("Correo o contraseña incorrectos");
                }
            }
        });
    }

    // Método que valida el usuario por correo y contraseña y retorna el rol
    private String validarUsuarioPorCorreo(String correo, String contraseña) {
        String rol = null;

        try (Connection conn = DatabaseConnector.getConnection()) {
            // Consulta SQL para verificar usuario y contraseña
            String sql = "SELECT rol FROM Usuarios WHERE nombre = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, correo);
                stmt.setString(2, contraseña);

                // Ejecutamos la consulta
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Si se encuentra un usuario válido, obtenemos el rol
                        rol = rs.getString("rol");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rol;
    }

    // Método que abre el menú principal dependiendo del rol seleccionado
    private void abrirMenu(String rol) {
        JFrame frame;
        if (rol.equals("Usuario")) {
            // Si el rol es 'usuario', se abre el formulario de usuarios
            frame = new JFrame("Formulario de Usuario");
            frame.setContentPane(new Formulario_de_usuarios().panel1);
        } else if (rol.equals("Administrador")) {
            // Si el rol es 'administrador', se abre el formulario de administrador
            frame = new JFrame("Formulario de Administrador");
            frame.setContentPane(new formulario_admin().panel1);
        } else {
            // Si no es un rol válido, no hacemos nada
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
