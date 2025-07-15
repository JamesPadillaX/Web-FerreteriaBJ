package dao;

import modelo.DetalleVenta;
import modelo.Venta;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    private Connection con;

    public VentaDAO() {
        con = Conexion.getInstancia().getConexion();
    }


    public int registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        int idVentaGenerada = -1;
        String sqlVenta = "INSERT INTO ventas (idCliente, idMetodoPago, metodoEnvio, total, estado, codigo_operacion, comprobante) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_venta (idVenta, idProducto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try {
            con.setAutoCommit(false);

            try (PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                psVenta.setInt(1, venta.getIdCliente());
                psVenta.setInt(2, venta.getIdMetodoPago());
                psVenta.setString(3, venta.getMetodoEnvio());
                psVenta.setDouble(4, venta.getTotal());
                psVenta.setString(5, venta.getEstado());
                psVenta.setString(6, venta.getCodigoOperacion());
                psVenta.setString(7, venta.getComprobante());
                psVenta.executeUpdate();

                ResultSet rs = psVenta.getGeneratedKeys();
                if (rs.next()) {
                    idVentaGenerada = rs.getInt(1);
                }
            }

            try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
                for (DetalleVenta d : detalles) {
                    psDetalle.setInt(1, idVentaGenerada);
                    psDetalle.setInt(2, d.getIdProducto());
                    psDetalle.setInt(3, d.getCantidad());
                    psDetalle.setDouble(4, d.getPrecioUnitario());
                    psDetalle.addBatch();
                }
                psDetalle.executeBatch();
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return idVentaGenerada;
    }

    // Actualizar estado de la venta (usado por admin)
    public boolean actualizarEstadoVenta(int idVenta, String nuevoEstado) {
        String sql = "UPDATE ventas SET estado = ? WHERE idVenta = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idVenta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener todas las ventas para el administrador
    public List<Venta> obtenerTodasLasVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.*, c.nombre AS nombreCliente, c.apellidos AS apellidosCliente, mp.nombre AS metodoPagoNombre " +
                     "FROM ventas v " +
                     "JOIN clientes c ON v.idCliente = c.idCliente " +
                     "JOIN metodos_pago mp ON v.idMetodoPago = mp.idMetodoPago " +
                     "ORDER BY v.fecha DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Venta v = new Venta();
                v.setIdVenta(rs.getInt("idVenta"));
                v.setIdCliente(rs.getInt("idCliente"));
                v.setIdMetodoPago(rs.getInt("idMetodoPago"));
                v.setMetodoEnvio(rs.getString("metodoEnvio"));
                v.setTotal(rs.getDouble("total"));
                v.setEstado(rs.getString("estado"));
                v.setCodigoOperacion(rs.getString("codigo_operacion"));
                v.setComprobante(rs.getString("comprobante"));

                // Convertir Timestamp a String con formato corto si tu modelo usa String
                Timestamp fechaTS = rs.getTimestamp("fecha");
                v.setFecha(fechaTS != null ? fechaTS.toString() : null);

                // Extras para mostrar en JSP
                v.setNombre(rs.getString("nombreCliente") + " " + rs.getString("apellidosCliente"));
                v.setNombreMetodoPago(rs.getString("metodoPagoNombre"));

                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Obtener todas las ventas para el administrador filtradas por fecha
public List<Venta> obtenerVentasPorFecha(String fechaInicio, String fechaFin) {
    List<Venta> lista = new ArrayList<>();
    // Modificar la consulta SQL para agregar el filtro de fechas
    String sql = "SELECT v.*, c.nombre AS nombreCliente, c.apellidos AS apellidosCliente, mp.nombre AS metodoPagoNombre " +
                 "FROM ventas v " +
                 "JOIN clientes c ON v.idCliente = c.idCliente " +
                 "JOIN metodos_pago mp ON v.idMetodoPago = mp.idMetodoPago " +
                 "WHERE v.fecha BETWEEN ? AND ? " + // Filtro por fecha
                 "ORDER BY v.fecha DESC";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        // Establecer los valores de fechaInicio y fechaFin en la consulta
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Venta v = new Venta();
            v.setIdVenta(rs.getInt("idVenta"));
            v.setIdCliente(rs.getInt("idCliente"));
            v.setIdMetodoPago(rs.getInt("idMetodoPago"));
            v.setMetodoEnvio(rs.getString("metodoEnvio"));
            v.setTotal(rs.getDouble("total"));
            v.setEstado(rs.getString("estado"));
            v.setCodigoOperacion(rs.getString("codigo_operacion"));
            v.setComprobante(rs.getString("comprobante"));

            // Convertir Timestamp a String con formato corto si tu modelo usa String
            Timestamp fechaTS = rs.getTimestamp("fecha");
            v.setFecha(fechaTS != null ? fechaTS.toString() : null);

            // Extras para mostrar en JSP
            v.setNombre(rs.getString("nombreCliente") + " " + rs.getString("apellidosCliente"));
            v.setNombreMetodoPago(rs.getString("metodoPagoNombre"));

            lista.add(v);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}
// Listar el detalle de una venta (productos comprados en esa venta)
public List<DetalleVenta> obtenerDetalleVenta(int idVenta) {
    List<DetalleVenta> lista = new ArrayList<>();

    String sql = "SELECT dv.idDetalleVenta, dv.idVenta, dv.idProducto, p.nombre AS nombreProducto, " +
                 "dv.cantidad, dv.precio_unitario " +
                 "FROM detalle_venta dv " +
                 "JOIN productos p ON dv.idProducto = p.idProducto " +
                 "WHERE dv.idVenta = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idVenta);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdDetalleVenta(rs.getInt("idDetalleVenta"));
            detalle.setIdVenta(rs.getInt("idVenta"));
            detalle.setIdProducto(rs.getInt("idProducto"));
            detalle.setNombreProducto(rs.getString("nombreProducto"));
            detalle.setCantidad(rs.getInt("cantidad"));
            detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));

            lista.add(detalle);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}

}
