package dao;

import modelo.Permiso;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PermisoDAO {
    private static final Logger LOGGER = Logger.getLogger(PermisoDAO.class.getName());
    private Connection con;

    public PermisoDAO() {
        con = Conexion.getInstancia().getConexion();
    }

    // Listar todos los permisos con su módulo
    public List<Permiso> listarPermisos() {
        List<Permiso> lista = new ArrayList<>();
        String sql = "SELECT p.idPermiso, p.nombre, p.idModulo, m.nombre AS nombreModulo " +
                     "FROM permisos p " +
                     "JOIN modulos m ON p.idModulo = m.id " +
                     "ORDER BY m.nombre, p.nombre";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Permiso p = new Permiso();
                p.setIdPermiso(rs.getInt("idPermiso"));
                p.setNombre(rs.getString("nombre"));
                p.setIdModulo(rs.getInt("idModulo"));
                lista.add(p);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar permisos", e);
        }
        return lista;
    }

    // Listar permisos asignados a un rol
    public List<Integer> listarPermisosPorRol(int idRol) {
        List<Integer> listaIds = new ArrayList<>();
        String sql = "SELECT idPermiso FROM rol_permiso WHERE idRol = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listaIds.add(rs.getInt("idPermiso"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar permisos por rol ID: " + idRol, e);
        }
        return listaIds;
    }

    // Eliminar todos los permisos de un rol
    public boolean eliminarPermisosDeRol(int idRol) {
        String sql = "DELETE FROM rol_permiso WHERE idRol = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar permisos del rol ID: " + idRol, e);
            return false;
        }
    }

    // Asignar un permiso a un rol
    public boolean asignarPermisoRol(int idRol, int idPermiso) {
        String sql = "INSERT INTO rol_permiso (idRol, idPermiso) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            ps.setInt(2, idPermiso);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al asignar permiso ID " + idPermiso + " al rol ID " + idRol, e);
            return false;
        }
    }

    // Obtener módulos permitidos por rol
    public List<String> obtenerModulosPermitidosPorRol(int idRol) {
        List<String> modulos = new ArrayList<>();
        String sql = "SELECT DISTINCT m.nombre " +
                     "FROM rol_permiso rp " +
                     "JOIN permisos p ON rp.idPermiso = p.idPermiso " +
                     "JOIN modulos m ON p.idModulo = m.id " +
                     "WHERE rp.idRol = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    modulos.add(rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener módulos permitidos para el rol ID: " + idRol, e);
        }
        return modulos;
    }

    // Obtener nombre del rol por ID
    public String obtenerNombreRolPorId(int idRol) {
        String nombre = null;
        String sql = "SELECT nombre FROM roles WHERE idRol = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nombre = rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener nombre del rol ID: " + idRol, e);
        }
        return nombre;
    }
}
