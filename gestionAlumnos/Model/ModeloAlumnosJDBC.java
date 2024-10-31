package gestionAlumnos.Model;

import java.sql.Connection;

import gestionAlumnos.UI.VentanaAlumnos;
import gestionAlumnos.UI.VentanaAlumnos.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModeloAlumnosJDBC implements IModeloAlumnos {

	private static String cadenaConexion = "jdbc:mysql://localhost:3306/adat1";
	private static String user = "dam2";
	private static String pass = "asdf.1234";

	public ModeloAlumnosJDBC() {

	}

	@Override
	public List<String> getAll() {
    List<String> alumnos = new ArrayList<>();

    try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass);) {
        String sql = "SELECT * FROM alumnos";
        PreparedStatement sentencia = con.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            String dni = resultado.getString("dni");
            String nombre = resultado.getString("nombre");
            String apellido = resultado.getString("apellido");
            String cp = resultado.getString("cp");
            String alumno = dni+" "+nombre + " " + apellido+" "+cp;
            alumnos.add(alumno); 
        
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return alumnos;
	}

	@Override
	public Alumno getAlumnoPorDNI(String DNI) {
		try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass);) {

			String sql = "select * from alumnos where dni='" + DNI + "'";
			PreparedStatement sentencia = con.prepareStatement(sql);
			ResultSet resultado = sentencia.executeQuery();
			while (resultado.next()) {
				System.out.println("DNI: " + resultado.getString(1));
				System.out.println("Nombre: " + resultado.getString(2));
				System.out.println("Apellido: " + resultado.getString(3));
				System.out.println("CP: " + resultado.getString(4));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	@Override
	public boolean modificarAlumno(Alumno alumno) {
		try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass);) {

			String sql = "update alumnos set nombre='" + alumno.getNombre() + "', apellido='" + alumno.getApellidos()
					+ "',cp='" + alumno.getCP() + "' where dni='" + alumno.getDNI() + "'";
			PreparedStatement update = con.prepareStatement(sql);

			update.executeQuery();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean eliminarAlumno(String DNI) {
		try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass);) {

			String sql = "DELETE FROM alumnos WHERE dni ='" + DNI + "'";
			PreparedStatement delete = con.prepareStatement(sql);
			delete.executeQuery();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean crear(Alumno alumno) {
		try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass);) {

			String sql = "Insert into alumnos(dni,nombre,apellido,cp) values(?,?,?,?)";
			PreparedStatement insert = con.prepareStatement(sql);

			insert.setString(1, alumno.getDNI());
			insert.setString(2, alumno.getNombre());
			insert.setString(3, alumno.getApellidos());
			insert.setString(4, alumno.getCP());
			insert.executeQuery();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

}
