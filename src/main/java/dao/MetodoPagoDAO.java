package dao;

import modelo.MetodoPago;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAO {

    private Connection con;

    public MetodoPagoDAO() {
        con = Conexion.getInstancia().getConexion();
    }

 
    public List<MetodoPago> listarMetodosPago() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM metodos_pago WHERE estado <> 2 ORDER BY idMetodoPago";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MetodoPago metodo = new MetodoPago();
                metodo.setIdMetodoPago(rs.getInt("idMetodoPago"));
                metodo.setNombre(rs.getString("nombre"));
                metodo.setDescripcion(rs.getString("descripcion"));
                metodo.setImagen(rs.getString("imagen"));
                metodo.setEstado(rs.getInt("estado"));
                lista.add(metodo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

 
    public List<MetodoPago> listarMetodosPagoActivos() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM metodos_pago WHERE estado = 1 ORDER BY idMetodoPago";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MetodoPago metodo = new MetodoPago();
                metodo.setIdMetodoPago(rs.getInt("idMetodoPago"));
                metodo.setNombre(rs.getString("nombre"));
                metodo.setDescripcion(rs.getString("descripcion"));
                metodo.setImagen(rs.getString("imagen"));
                metodo.setEstado(rs.getInt("estado"));
                lista.add(metodo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Guardar nuevo método
    public boolean guardarMetodoPago(MetodoPago metodo) {
        String sql = "INSERT INTO metodos_pago(nombre, descripcion, imagen, estado) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, metodo.getNombre());
            ps.setString(2, metodo.getDescripcion());
            ps.setString(3, metodo.getImagen());
            ps.setInt(4, metodo.getEstado());
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public MetodoPago obtenerMetodoPagoPorId(int idMetodoPago) {
        MetodoPago metodo = null;
        String sql = "SELECT * FROM metodos_pago WHERE idMetodoPago = ? AND estado <> 2";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMetodoPago);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    metodo = new MetodoPago();
                    metodo.setIdMetodoPago(rs.getInt("idMetodoPago"));
                    metodo.setNombre(rs.getString("nombre"));
                    metodo.setDescripcion(rs.getString("descripcion"));
                    metodo.setImagen(rs.getString("imagen"));
                    metodo.setEstado(rs.getInt("estado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metodo;
    }


    public boolean modificarMetodoPago(MetodoPago metodo) {
        String sql = "UPDATE metodos_pago SET nombre = ?, descripcion = ?, imagen = ?, estado = ? WHERE idMetodoPago = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, metodo.getNombre());
            ps.setString(2, metodo.getDescripcion());
            ps.setString(3, metodo.getImagen());
            ps.setInt(4, metodo.getEstado());
            ps.setInt(5, metodo.getIdMetodoPago());
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean cambiarEstadoMetodoPago(int idMetodoPago, int nuevoEstado) {
        String sql = "UPDATE metodos_pago SET estado = ? WHERE idMetodoPago = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstado);
            ps.setInt(2, idMetodoPago);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarMetodoPago(int idMetodoPago) {
        return cambiarEstadoMetodoPago(idMetodoPago, 2);
    }
}
