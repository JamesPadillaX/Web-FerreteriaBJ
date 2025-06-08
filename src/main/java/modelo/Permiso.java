package modelo;

public class Permiso {
    private int idPermiso;
    private String nombre;
    private int idModulo;

    public Permiso() {
    }

    public Permiso(String nombre, int idModulo) {
        this.nombre = nombre;
        this.idModulo = idModulo;
    }


    public Permiso(int idPermiso, String nombre, int idModulo) {
        this.idPermiso = idPermiso;
        this.nombre = nombre;
        this.idModulo = idModulo;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
