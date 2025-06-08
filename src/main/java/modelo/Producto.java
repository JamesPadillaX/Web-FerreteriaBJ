package modelo;

public class Producto {

    private int idProducto;
    private int idCategoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidad;
    private int estado;
    private String categoria; // nombre de la categoría
    private String imagen;    // nombre o ruta de la imagen

    public Producto() {}

    public Producto(int idProducto, int idCategoria, String nombre, String descripcion, double precio, int cantidad, int estado, String imagen) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.estado = estado;
        this.imagen = imagen;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstadoTexto() {
        switch (estado) {
            case 1:
                return "ACTIVO";
            case 0:
                return "INACTIVO";
            case 2:
                return "ELIMINADO";
            default:
                return "DESCONOCIDO";
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
