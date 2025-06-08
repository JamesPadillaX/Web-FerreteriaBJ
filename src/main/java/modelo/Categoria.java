package modelo;

public class Categoria {
    private int idCategoria;
    private String nombre;
    private int estado; 

    public Categoria() {
    }

    public Categoria(int idCategoria, String nombre, int estado) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
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
