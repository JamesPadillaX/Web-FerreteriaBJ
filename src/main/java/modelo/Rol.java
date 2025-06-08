package modelo;

public class Rol {
    private int idRol;
    private String nombre;
    private int estado; 

    public Rol() {
    }

    public Rol(int idRol, String nombre, int estado) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getEstadoTexto() {
        switch (estado) {
            case 1: return "ACTIVO";
            case 0: return "INACTIVO";
            case 2: return "ELIMINADO";
            default: return "DESCONOCIDO";
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
