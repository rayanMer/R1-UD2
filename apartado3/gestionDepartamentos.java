package apartado3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class gestionDepartamentos {
	private static String cadenaConexion = "jdbc:mysql://localhost:3306/employees";
	private static String user = "root";
	private static String pass = "11391970";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione una opción:");
        System.out.println("1. Mostrar todos los países");
        System.out.println("2. Crear un país");
        System.out.println("3. Modificar un país");
        System.out.println("4. Eliminar un país");
        int opt = scanner.nextInt();

        switch (opt) {
            case 1:
                mostrarTodos();
                break;
            case 2:
                System.out.println("Introduce ID del país:");
                String id = scanner.next();
                System.out.println("Introduce el nombre del país:");
                String nombre = scanner.next();
                System.out.println("Introduce el ID de la región:");
                int regionId = scanner.nextInt();
                while(regionId<1||regionId>4) {
                	System.err.println("Introduce un id de region valido que este includio en la siguiente tabla: ");
                	System.out.println("+-----------+------------------------+");
                	System.out.println("| region_id | region_name            |");
                	System.out.println("+-----------+------------------------+");
                	System.out.println("|         1 | Europe                 |");
                	System.out.println("|         2 | Americas               |");
                	System.out.println("|         3 | Asia                   |");
                	System.out.println("|         4 | Middle East and Africa |");
                	System.out.println("+-----------+------------------------+");
                	regionId=scanner.nextInt();

                }
                Countries country = new Countries(id, nombre, regionId);
                crearCountry(country);
                System.out.println("Introducido correctamente");
                break;
            case 3:
                System.out.println("Introduce el ID del país a modificar:");
                String modId = scanner.next();
                System.out.println("Introduce el nuevo nombre del país:");
                String nuevoNombre = scanner.next();
                System.out.println("Introduce el nuevo ID de la región:");
                int nuevaRegionId = scanner.nextInt();
                while(nuevaRegionId<1||nuevaRegionId>4) {
                	System.err.println("Introduce una nueva RegionId valido que este includio en la siguiente tabla: ");
                	System.out.println("+-----------+------------------------+");
                	System.out.println("| region_id | region_name            |");
                	System.out.println("+-----------+------------------------+");
                	System.out.println("|         1 | Europe                 |");
                	System.out.println("|         2 | Americas               |");
                	System.out.println("|         3 | Asia                   |");
                	System.out.println("|         4 | Middle East and Africa |");
                	System.out.println("+-----------+------------------------+");
                	nuevaRegionId=scanner.nextInt();
                }
                Countries countryMod = new Countries(modId, nuevoNombre, nuevaRegionId);
                modificarPais(countryMod);
                break;
            case 4:
                System.out.println("Introduce el ID del país a eliminar:");
                String elimId = scanner.next();
                eliminarPais(elimId);
                break;
            default:
                System.err.println("Opción incorrecta");
        }

        scanner.close();
    }

    public static void mostrarTodos() {
        System.out.println("Mostrando todos los países...");
        try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass)) {
            String sql = "select * from countries";
            PreparedStatement mostrar = con.prepareStatement(sql);
            ResultSet resultado= mostrar.executeQuery();
            while(resultado.next()) {
            	System.out.println("Country_ID: "+resultado.getString(1)+", Country_Name: "+resultado.getString(2)
            	+", Region_ID: "+resultado.getString(3));
            	System.out.println("----------------------------------------------------------------");

            }
        } catch (SQLException e) {
            System.out.println("Error al crear country: " + e.getMessage());
        }
    }

    public static boolean crearCountry(Countries country) {
        try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass)) {
            String sql = "INSERT INTO countries (country_id, country_name, region_id) VALUES (?, ?, ?)";
            PreparedStatement insert = con.prepareStatement(sql);
            insert.setString(1, country.getCountry_id());
            insert.setString(2, country.getCountry_name());
            insert.setInt(3, country.getRegion_id());
            insert.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al crear country: " + e.getMessage());
        }
        return false;
    }


    public static boolean modificarPais(Countries country) {
    	try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass)) {
    		String sql = "update countries set country_name = ?, region_id = ? where country_id = ?";
            PreparedStatement update = con.prepareStatement(sql);
            update.setString(1, country.getCountry_name());
            update.setInt(2, country.getRegion_id());
            update.setString(3, country.getCountry_id());
            update.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar country: " + e.getMessage());
        }
        return false;
    }

    public static boolean eliminarPais(String countryId) {
    	try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass)) {
    		String sql = "delete from countries where country_id = ?";
            PreparedStatement delete = con.prepareStatement(sql);
            delete.setString(1, countryId);
            delete.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar country: " + e.getMessage());
        }
        return false;    }
}
