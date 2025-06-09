package dao;

import modelo.DetalleCarrito;
import modelo.Producto;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAO {

    private Connection con;

    public CarritoDAO() {
        con = Conexion.getInstancia().getConexion();
    }

    // 1. Crear nuevo carrito para cliente (si no tiene uno activo)
    public int crearCarritoSiNoExiste(int idCliente) {
        int idCarrito = obtenerCarritoActivo(idCliente);
        if (idCarrito != -1) return idCarrito;

        String sql = "INSERT INTO carritos (idCliente) VALUES (?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idCliente);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 2. Obtener el carrito activo de un cliente
    public int obtenerCarritoActivo(int idCliente) {
        String sql = "SELECT idCarrito FROM carritos WHERE idCliente = ? AND estado = 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("idCarrito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 3. Agregar producto al carrito
    public boolean agregarProductoAlCarrito(int idCarrito, int idProducto, int cantidad) {
        // Verificamos si ya existe ese producto en el carrito
        String verificar = "SELECT cantidad FROM detalle_carrito WHERE idCarrito = ? AND idProducto = ?";
        try (PreparedStatement ps = con.prepareStatement(verificar)) {
            ps.setInt(1, idCarrito);
            ps.setInt(2, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Ya existe → actualizar cantidad
                int nuevaCantidad = rs.getInt("cantidad") + cantidad;
                String actualizar = "UPDATE detalle_carrito SET cantidad = ? WHERE idCarrito = ? AND idProducto = ?";
                try (PreparedStatement ps2 = con.prepareStatement(actualizar)) {
                    ps2.setInt(1, nuevaCantidad);
                    ps2.setInt(2, idCarrito);
                    ps2.setInt(3, idProducto);
                    return ps2.executeUpdate() > 0;
                }
            } else {
                // No existe → insertar nuevo
                String insertar = "INSERT INTO detalle_carrito (idCarrito, idProducto, cantidad) VALUES (?, ?, ?)";
                try (PreparedStatement ps2 = con.prepareStatement(insertar)) {
                    ps2.setInt(1, idCarrito);
                    ps2.setInt(2, idProducto);
                    ps2.setInt(3, cantidad);
                    return ps2.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Obtener los productos del carrito
    public List<DetalleCarrito> listarDetallePorCarrito(int idCarrito) {
        List<DetalleCarrito> lista = new ArrayList<>();
        String sql = "SELECT d.idDetalleCarrito, d.idCarrito, d.idProducto, d.cantidad, " +
             "p.nombre, p.imagen, p.precio FROM detalle_carrito d " +
             "JOIN productos p ON d.idProducto = p.idProducto " +
             "WHERE d.idCarrito = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCarrito);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetalleCarrito dc = new DetalleCarrito();
                dc.setIdDetalleCarrito(rs.getInt("idDetalleCarrito"));
                dc.setIdCarrito(rs.getInt("idCarrito"));
                dc.setIdProducto(rs.getInt("idProducto"));
                dc.setCantidad(rs.getInt("cantidad"));

                Producto prod = new Producto();
                prod.setIdProducto(rs.getInt("idProducto"));
                prod.setNombre(rs.getString("nombre"));
                prod.setPrecio(rs.getDouble("precio")); 
                prod.setImagen(rs.getString("imagen"));

                dc.setProducto(prod);

                lista.add(dc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 5. Eliminar un producto del carrito
    public boolean eliminarProductoDelCarrito(int idCarrito, int idProducto) {
        String sql = "DELETE FROM detalle_carrito WHERE idCarrito = ? AND idProducto = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCarrito);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. Vaciar todo el carrito
    public boolean vaciarCarrito(int idCarrito) {
        String sql = "DELETE FROM detalle_carrito WHERE idCarrito = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCarrito);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 7. Finalizar carrito (cambiar estado)
    public boolean finalizarCarrito(int idCarrito) {
        String sql = "UPDATE carritos SET estado = 0 WHERE idCarrito = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCarrito);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
