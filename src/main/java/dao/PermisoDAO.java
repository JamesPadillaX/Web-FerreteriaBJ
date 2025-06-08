package dao;

import modelo.Permiso;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermisoDAO {
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
                // Puedes agregar un campo "nombreModulo" en Permiso si lo vas a mostrar en el futuro
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return listaIds;
    }

    // Eliminar todos los permisos de un rol (para luego reasignar)
    public boolean eliminarPermisosDeRol(int idRol) {
        String sql = "DELETE FROM rol_permiso WHERE idRol = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
    }

    // Obtener nombres únicos de módulos permitidos para un rol
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
            e.printStackTrace();
        }
        return modulos;
    }

    // ✅ Nuevo: Obtener el nombre del rol según su ID
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
            e.printStackTrace();
        }

        return nombre;
    }
}
