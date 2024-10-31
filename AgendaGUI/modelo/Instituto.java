package modelo;

public class Instituto {
    private String nombre;
    private String ciudad;
    private String codigoPostal;

    public Instituto(String nombre, String ciudad, String codigoPostal) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }
}
