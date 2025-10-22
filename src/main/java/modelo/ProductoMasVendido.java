package modelo;

public class ProductoMasVendido {

    private int idProducto;
    private String nombre;
    private String categoria;
    private int totalVendido;
    private double montoGenerado;

    public ProductoMasVendido() {
    }

    public ProductoMasVendido(int idProducto, String nombre, String categoria, int totalVendido, double montoGenerado) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.totalVendido = totalVendido;
        this.montoGenerado = montoGenerado;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(int totalVendido) {
        this.totalVendido = totalVendido;
    }

    public double getMontoGenerado() {
        return montoGenerado;
    }

    public void setMontoGenerado(double montoGenerado) {
        this.montoGenerado = montoGenerado;
    }

    @Override
    public String toString() {
        return "ProductoMasVendido{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", totalVendido=" + totalVendido +
                ", montoGenerado=" + montoGenerado +
                '}';
    }
}
