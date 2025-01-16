package RECUPERACION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Administrador1 {
    private JPanel panel1; // Panel principal
    private JTabbedPane tabbedPane1; // Primer TabbedPane (Productos)
    private JTabbedPane tabbedPane2; // Segundo TabbedPane (Usuarios)
    private JTabbedPane tabbedPane3; // Tercer TabbedPane (Listar Productos)
    private JTabbedPane tabbedPane4; // Cuarto TabbedPane (Listar Usuarios)

    // Campos para "Productos" (tabbedPane1)
    private JTextField textField1; // Campo para "Nombre"
    private JTextField textField2; // Campo para "Descripción"
    private JTextField textField3; // Campo para "Precio"
    private JTextField textField4; // Campo para "Stock"
    private JButton agregarButton; // Botón para agregar productos

    // Campos para "Usuarios" (tabbedPane2)
    private JTextField textField5; // Campo para "Usuario"
    private JPasswordField passwordField1; // Campo para "Password"
    private JTextField textField6; // Campo para "Rol"
    private JButton agregarButton1; // Botón para agregar usuarios

    // Botones para listar
    private JButton listarButton; // Botón para listar productos
    private JButton listarButton1; // Botón para listar usuarios
    private JTable table1;
    private JTable table2;

    // Listas para almacenar productos y usuarios
    private ArrayList<String> productos = new ArrayList<>();
    private ArrayList<String> usuarios = new ArrayList<>();

    public Administrador1() {
        // Acción del botón "Agregar" en tabbedPane1
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener valores de los campos
                String nombre = textField1.getText();
                String descripcion = textField2.getText();
                String precio = textField3.getText();
                String stock = textField4.getText();

                // Validación simple
                if (nombre.isEmpty() || descripcion.isEmpty() || precio.isEmpty() || stock.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Agregar a la lista de productos
                    productos.add("Producto: " + nombre + ", Descripción: " + descripcion + ", Precio: " + precio + ", Stock: " + stock);
                    JOptionPane.showMessageDialog(null, "Producto agregado con éxito.");
                }
            }
        });

        // Acción del botón "Agregar" en tabbedPane2
        agregarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener valores de los campos
                String usuario = textField5.getText();
                String password = new String(passwordField1.getPassword());
                String rol = textField6.getText();

                // Validación simple
                if (usuario.isEmpty() || password.isEmpty() || rol.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Agregar a la lista de usuarios
                    usuarios.add("Usuario: " + usuario + ", Rol: " + rol);
                    JOptionPane.showMessageDialog(null, "Usuario agregado con éxito.");
                }
            }
        });

        // Acción del botón "Listar Productos" en tabbedPane3
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar si hay productos en la lista
                if (productos.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay productos registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Mostrar los productos en consola o en un cuadro de diálogo
                    StringBuilder listaProductos = new StringBuilder("Lista de Productos:\n");
                    for (String producto : productos) {
                        listaProductos.append(producto).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, listaProductos.toString(), "Productos", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Acción del botón "Listar Usuarios" en tabbedPane4
        listarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar si hay usuarios en la lista
                if (usuarios.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay usuarios registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Mostrar los usuarios en consola o en un cuadro de diálogo
                    StringBuilder listaUsuarios = new StringBuilder("Lista de Usuarios:\n");
                    for (String usuario : usuarios) {
                        listaUsuarios.append(usuario).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, listaUsuarios.toString(), "Usuarios", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Administrador");
        frame.setContentPane(new Administrador1().panel1); // Asegúrate de que el diseño esté vinculado
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // Ajusta el tamaño automáticamente
        frame.setVisible(true); // Muestra la ventana
    }
}
