import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Formulario_admin {
    public JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField ingreso_de_nombre;
    private JTextField ingreso_de_precio;
    private JTextField ingreso_de_descripcion;
    private JSpinner ingreso_de_stock;
    private JButton agregarProductoButton;
    private JTable tabla_de_productostabla_de_productos;
    private JTextField ingreso_de_nombre_de_usuarionuevo;
    private JTextField ingreso_de_password;
    private JComboBox<String> ingreso_de_rol;
    private JButton boton_de_agregar_nuevo;
    private JTable tabla_de_todos_usuarios;

    public Formulario_admin() {
        // Configurar el modelo para el JComboBox de roles
        ingreso_de_rol.removeAllItems();
        ingreso_de_rol.addItem("usuario");
        ingreso_de_rol.addItem("administrador");

        // Configurar el modelo para el JSpinner
        SpinnerNumberModel modelo = new SpinnerNumberModel(10, 10, 350, 1);
        ingreso_de_stock.setModel(modelo);

        JFormattedTextField editor = ((JSpinner.DefaultEditor) ingreso_de_stock.getEditor()).getTextField();
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validateInput(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validateInput(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validateInput(); }

            private void validateInput() {
                try {
                    int inputValue = Integer.parseInt(editor.getText());
                    if (inputValue > 350) {
                        SwingUtilities.invokeLater(() -> ingreso_de_stock.setValue(350));
                    }
                } catch (NumberFormatException ex) { }
            }
        });

        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { agregarProducto(); }
        });

        boton_de_agregar_nuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { agregarUsuario(); }
        });

        configurarTablaProductos();
        configurarTablaUsuarios();
        cargarDatosEnTablaProductos();
        cargarDatosEnTablaUsuarios();
    }

    private void configurarTablaProductos() {
        String[] columnas = {"ID Producto", "Nombre", "Descripción", "Precio", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tabla_de_productostabla_de_productos.setModel(modelo);
        tabla_de_productostabla_de_productos.setDefaultEditor(Object.class, null);
    }

    private void configurarTablaUsuarios() {
        String[] columnas = {"ID Usuario", "Nombre de Usuario", "Rol"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tabla_de_todos_usuarios.setModel(modelo);
        tabla_de_todos_usuarios.setDefaultEditor(Object.class, null);
    }

    private void cargarDatosEnTablaProductos() {
        String query = "SELECT * FROM productos";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            DefaultTableModel modelo = (DefaultTableModel) tabla_de_productostabla_de_productos.getModel();
            modelo.setRowCount(0);

            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");

                Object[] fila = {idProducto, nombre, descripcion, precio, stock};
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error al cargar los datos: " + e.getMessage());
        } catch (Exception e) { // Captura cualquier otra excepción no prevista
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error inesperado: " + e.getMessage());
        }
    }

    private void cargarDatosEnTablaUsuarios() {
        String query = "SELECT * FROM usuarios";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            DefaultTableModel modelo = (DefaultTableModel) tabla_de_todos_usuarios.getModel();
            modelo.setRowCount(0);

            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("nombre_usuario");
                String rol = rs.getString("rol");

                Object[] fila = {idUsuario, nombreUsuario, rol};
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error al cargar los datos: " + e.getMessage());
        } catch (Exception e) { // Captura cualquier otra excepción no prevista
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error inesperado: " + e.getMessage());
        }
    }

    private void agregarProducto() {
        String nombre = ingreso_de_nombre.getText();
        String descripcion = ingreso_de_descripcion.getText();
        double precio = Double.parseDouble(ingreso_de_precio.getText());
        int stock = (int) ingreso_de_stock.getValue();

        String query = "INSERT INTO productos (nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setDouble(3, precio);
            pstmt.setInt(4, stock);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(panel1, "Producto agregado exitosamente.");
            // Limpiar los campos después de agregar el producto
            ingreso_de_nombre.setText("");
            ingreso_de_precio.setText("");
            ingreso_de_descripcion.setText("");
            ingreso_de_stock.setValue(10);
            cargarDatosEnTablaProductos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error al agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) { // Captura cualquier otra excepción no prevista
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error inesperado: " + e.getMessage());
        }
    }

    private void agregarUsuario() {
        String nombreUsuario = ingreso_de_nombre_de_usuarionuevo.getText();
        String password = ingreso_de_password.getText();
        String rol = (String) ingreso_de_rol.getSelectedItem();

        String query = "INSERT INTO usuarios (nombre_usuario, password, rol) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, password);
            pstmt.setString(3, rol);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(panel1, "Usuario agregado exitosamente.");
            ingreso_de_nombre_de_usuarionuevo.setText("");
            ingreso_de_password.setText("");
            ingreso_de_rol.setSelectedIndex(0);
            cargarDatosEnTablaUsuarios();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error al agregar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) { // Captura cualquier otra excepción no prevista
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error inesperado: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Formulario Admin");
        Formulario_admin formulario = new Formulario_admin();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(formulario.panel1);
        frame.pack();
        frame.setVisible(true);
    }
}
