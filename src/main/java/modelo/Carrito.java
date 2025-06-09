package modelo;

import java.security.Timestamp;

public class Carrito {
    private int idCarrito;
    private int idCliente;
    private Timestamp fechaCreacion;
    private int estado;

    
    public Carrito(int idCarrito, int idCliente, Timestamp fechaCreacion, int estado) {
        this.idCarrito = idCarrito;
        this.idCliente = idCliente;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }
    
    public Carrito(int idCliente, Timestamp fechaCreacion, int estado) {
        this.idCliente = idCliente;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    
    public Carrito() {
    }

    public int getIdCarrito() {
        return idCarrito;
    }
    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }

    

}
