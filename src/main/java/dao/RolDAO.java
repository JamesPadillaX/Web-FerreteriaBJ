package dao;

import modelo.Rol;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolDAO {

    private static final Logger logger = LoggerFactory.getLogger(RolDAO.class);
    private Connection con;

    public RolDAO() {
        con = Conexion.getInstancia().getConexion();
    }

    public List<Rol> listarRoles() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM roles WHERE estado <> 2 ORDER BY idRol";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("idRol"));
                rol.setNombre(rs.getString("nombre"));
                rol.setEstado(rs.getInt("estado"));
                lista.add(rol);
            }

        } catch (SQLException e) {
            logger.error("Error al listar roles", e);
        }
        return lista;
    }

    public boolean guardarRol(Rol rol) {
        String sql = "INSERT INTO roles(nombre, estado) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rol.getNombre());
            ps.setInt(2, 1);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            logger.error("Error al guardar rol", e);
        }
        return false;
    }

    public Rol obtenerRolPorId(int idRol) {
        Rol rol = null;
        String sql = "SELECT * FROM roles WHERE idRol = ? AND estado <> 2";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rol = new Rol();
                    rol.setIdRol(rs.getInt("idRol"));
                    rol.setNombre(rs.getString("nombre"));
                    rol.setEstado(rs.getInt("estado"));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener rol por ID", e);
        }
        return rol;
    }

    public boolean modificarRol(Rol rol) {
        String sql = "UPDATE roles SET nombre = ?, estado = ? WHERE idRol = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rol.getNombre());
            ps.setInt(2, rol.getEstado());
            ps.setInt(3, rol.getIdRol());
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            logger.error("Error al modificar rol", e);
        }
        return false;
    }

    public boolean cambiarEstadoRol(int idRol, int nuevoEstado) {
        String sql = "UPDATE roles SET estado = ? WHERE idRol = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstado);
            ps.setInt(2, idRol);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            logger.error("Error al cambiar estado del rol", e);
        }
        return false;
    }

    public boolean eliminarRol(int idRol) {
        return cambiarEstadoRol(idRol, 2);
    }

    public List<Rol> listarRolesActivos() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM roles WHERE estado = 1 ORDER BY idRol";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("idRol"));
                rol.setNombre(rs.getString("nombre"));
                rol.setEstado(rs.getInt("estado"));
                lista.add(rol);
            }

        } catch (SQLException e) {
            logger.error("Error al listar roles activos", e);
        }
        return lista;
    }

    public boolean existeRolPorNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM roles WHERE nombre = ? AND estado <> 2";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al verificar existencia de rol por nombre", e);
        }
        return false;
    }

    public Rol obtenerRolPorNombre(String nombre) {
        Rol rol = null;
        String sql = "SELECT * FROM roles WHERE nombre = ? AND estado <> 2";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rol = new Rol();
                    rol.setIdRol(rs.getInt("idRol"));
                    rol.setNombre(rs.getString("nombre"));
                    rol.setEstado(rs.getInt("estado"));
                }  
            }
        } catch (SQLException e) {
            logger.error("Error al obtener rol por nombre", e);
        }
    
        return rol;
    }
    

}
