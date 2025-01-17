import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Formulario_de_usuarios {
    public JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTable tabla_de_productostabla_de_productos; // Corregir el nombre de la variable
    private JTable tabla_producto_bajo;

    public Formulario_de_usuarios() {
        // Inicializar los componentes de la interfaz
        configurarTabla();
        cargarDatosEnTabla();
        cargarProductosBajoStock(); // Llamar a la función para cargar productos con stock <= 20
    }

    private void configurarTabla() {
        // Definir las columnas para la tabla de productos
        String[] columnas = {"ID Producto", "Nombre", "Descripción", "Precio", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tabla_de_productostabla_de_productos.setModel(modelo); // Usar el nombre correcto de la variable
        // Deshabilitar la edición de celdas
        tabla_de_productostabla_de_productos.setDefaultEditor(Object.class, null); // Usar el nombre correcto de la variable

        // Configuración de la tabla para los productos con bajo stock
        String[] columnasBajoStock = {"ID Producto", "Nombre", "Descripción", "Precio", "Stock"};
        DefaultTableModel modeloBajoStock = new DefaultTableModel(null, columnasBajoStock);
        tabla_producto_bajo.setModel(modeloBajoStock); // Configurar la tabla de productos con bajo stock
        tabla_producto_bajo.setDefaultEditor(Object.class, null); // Deshabilitar edición
    }

    private void cargarDatosEnTabla() {
        String query = "SELECT * FROM productos"; // Cambiar la consulta SQL para productos

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            DefaultTableModel modelo = (DefaultTableModel) tabla_de_productostabla_de_productos.getModel(); // Usar el nombre correcto de la variable
            modelo.setRowCount(0); // Limpiar la tabla antes de cargar los datos

            // Recorrer los resultados de la consulta y añadirlos a la tabla
            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");

                // Crear un array de objetos con los datos de cada fila
                Object[] fila = new Object[] {
                        idProducto, // ID Producto
                        nombre, // Nombre
                        descripcion, // Descripción
                        precio, // Precio
                        stock // Stock
                };

                // Añadir la fila a la tabla
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error al cargar los datos: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error inesperado: " + e.getMessage());
        }
    }

    private void cargarProductosBajoStock() {
        String query = "SELECT * FROM productos WHERE stock <= 20"; // Filtrar productos con stock <= 20

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            DefaultTableModel modeloBajoStock = (DefaultTableModel) tabla_producto_bajo.getModel();
            modeloBajoStock.setRowCount(0); // Limpiar la tabla antes de cargar los datos

            // Recorrer los resultados de la consulta y añadirlos a la tabla
            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");

                // Crear un array de objetos con los datos de cada fila
                Object[] fila = new Object[] {
                        idProducto, // ID Producto
                        nombre, // Nombre
                        descripcion, // Descripción
                        precio, // Precio
                        stock // Stock
                };

                // Añadir la fila a la tabla
                modeloBajoStock.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error al cargar los productos con bajo stock: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error inesperado: " + e.getMessage());
        }
    }


}
