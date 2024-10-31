package control;

import modelo.Contacto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppAgenda {
    private static String cadenaConexion = "jdbc:mysql://localhost:3306/adat1";
    private static String user = "dam2";
    private static String pass = "asdf.1234";


    public List<Contacto> obtenerContactos() {
        List<Contacto> contactos = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(cadenaConexion, user, pass);
             PreparedStatement stmt = conn.prepareStatement("SELECT nombre, telefono FROM contactos");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                contactos.add(new Contacto(nombre, telefono));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactos;
    }


    public void añadirContacto(Contacto contacto) {
        String sql = "INSERT INTO contactos (nombre, telefono) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(cadenaConexion, user, pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contacto.getNombre());
            stmt.setString(2, contacto.getTeléfono());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarContacto(String nombreAntiguo, String nuevoNombre, String nuevoTelefono) {
        String sql = "UPDATE contactos SET nombre = ?, telefono = ? WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(cadenaConexion, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevoNombre);   
            pstmt.setString(2, nuevoTelefono);   
            pstmt.setString(3, nombreAntiguo);   

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Contacto actualizado correctamente.");
            } else {
                System.out.println("No se encontró el contacto para actualizar.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }



    public void borrarContacto(String nombre) {
        String sql = "DELETE FROM contactos WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(cadenaConexion, user, pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
