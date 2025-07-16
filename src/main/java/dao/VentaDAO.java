package dao;

import modelo.DetalleVenta;
import modelo.Venta;
import util.Conexion;

import java.sql.*;
import java.text.SimpleDateFormat;
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
                lista.add(construirVenta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

public List<Venta> obtenerVentasPorFecha(String fechaInicio, String fechaFin) {
    List<Venta> lista = new ArrayList<>();
    String sql = "SELECT v.*, c.nombre AS nombreCliente, c.apellidos AS apellidosCliente, mp.nombre AS metodoPagoNombre " +
                 "FROM ventas v " +
                 "JOIN clientes c ON v.idCliente = c.idCliente " +
                 "JOIN metodos_pago mp ON v.idMetodoPago = mp.idMetodoPago " +
                 "WHERE v.fecha BETWEEN ? AND ? " +
                 "ORDER BY v.fecha DESC";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, fechaInicio + " 00:00:00");
        ps.setString(2, fechaFin + " 23:59:59");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(construirVenta(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}


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

    private Venta construirVenta(ResultSet rs) throws SQLException {
        Venta v = new Venta();
        v.setIdVenta(rs.getInt("idVenta"));
        v.setIdCliente(rs.getInt("idCliente"));
        v.setIdMetodoPago(rs.getInt("idMetodoPago"));
        v.setMetodoEnvio(rs.getString("metodoEnvio"));
        v.setTotal(rs.getDouble("total"));
        v.setEstado(rs.getString("estado"));
        v.setCodigoOperacion(rs.getString("codigo_operacion"));
        v.setComprobante(rs.getString("comprobante"));

        Timestamp fechaTS = rs.getTimestamp("fecha");
        if (fechaTS != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaFormateada = sdf.format(fechaTS); 
            v.setFecha(fechaFormateada);
        } else {
            v.setFecha(null);
        }

        v.setNombre(rs.getString("nombreCliente") + " " + rs.getString("apellidosCliente"));
        v.setNombreMetodoPago(rs.getString("metodoPagoNombre"));

        return v;
    }


    public List<int[]> obtenerVentasPorMes(int anio) {
    List<int[]> datos = new ArrayList<>();
    String sql = "SELECT MONTH(fecha) AS mes, COUNT(*) AS totalVentas " +
                 "FROM ventas " +
                 "WHERE YEAR(fecha) = ? " +
                 "GROUP BY mes " +
                 "ORDER BY mes";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, anio);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int[] registro = new int[2];
            registro[0] = rs.getInt("mes");        
            registro[1] = rs.getInt("totalVentas"); 
            datos.add(registro);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return datos;
}
public List<Venta> obtenerVentasPorCliente(int idCliente) {
    List<Venta> lista = new ArrayList<>();
    String sql = "SELECT * FROM ventas WHERE idCliente = ? ORDER BY fecha DESC";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idCliente);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Venta v = new Venta();
            v.setIdVenta(rs.getInt("idVenta"));
            v.setIdCliente(rs.getInt("idCliente"));
            v.setTotal(rs.getDouble("total"));
            v.setFecha(rs.getTimestamp("fecha").toString());
            lista.add(v);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}
public List<DetalleVenta> obtenerProductosCompradosPorCliente(int idCliente) {
    List<DetalleVenta> lista = new ArrayList<>();
    String sql = "{CALL sp_obtener_productos_comprados_por_cliente(?)}";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idCliente);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdProducto(rs.getInt("idProducto"));
            detalle.setNombreProducto(rs.getString("nombre"));
            detalle.setImagenProducto(rs.getString("imagen"));
            detalle.setCantidad(rs.getInt("totalCantidad"));
            detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
            detalle.setFechaCompra(rs.getTimestamp("fecha").toString());
            detalle.setEstado(rs.getString("estado"));

            lista.add(detalle);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}

}
