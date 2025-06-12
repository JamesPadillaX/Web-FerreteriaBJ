package dao;

import modelo.Usuario;
import modelo.Rol;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarioDAO {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);
    private Connection con;

    public UsuarioDAO() {
        con = Conexion.getInstancia().getConexion();
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre AS nombreRol FROM usuarios u " +
                     "LEFT JOIN roles r ON u.idRol = r.idRol " +
                     "WHERE u.estado <> 2 ORDER BY u.idUsuario";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellidos(rs.getString("apellidos"));
                u.setDni(rs.getString("dni"));
                u.setTelefono(rs.getString("telefono"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setEstado(rs.getInt("estado"));
                u.setIdRol(rs.getInt("idRol"));

                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("idRol"));
                rol.setNombre(rs.getString("nombreRol"));
                u.setRol(rol);

                lista.add(u);
            }

        } catch (SQLException e) {
            logger.error("Error al listar usuarios", e);
        }
        return lista;
    }

    public boolean guardarUsuario(Usuario u) {
        Preconditions.checkNotNull(u, "El objeto Usuario no debe ser null");
        Preconditions.checkArgument(!u.getNombre().isEmpty(), "El nombre es obligatorio");
        Preconditions.checkArgument(!u.getApellidos().isEmpty(), "Los apellidos son obligatorios");
        Preconditions.checkArgument(!u.getDni().isEmpty(), "El DNI es obligatorio");
        Preconditions.checkArgument(!u.getUsername().isEmpty(), "El username es obligatorio");
        Preconditions.checkArgument(!u.getPassword().isEmpty(), "La contraseña es obligatoria");

        String sql = "INSERT INTO usuarios(nombre, apellidos, dni, telefono, username, password, estado, idRol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getDni());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getUsername());
            ps.setString(6, u.getPassword());
            ps.setInt(7, 1);
            ps.setInt(8, u.getIdRol());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al guardar usuario", e);
        }
        return false;
    }

    public boolean modificarUsuario(Usuario u) {
        String sql = "UPDATE usuarios SET nombre = ?, apellidos = ?, dni = ?, telefono = ?, username = ?, password = ?, estado = ?, idRol = ? WHERE idUsuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getDni());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getUsername());
            ps.setString(6, u.getPassword());
            ps.setInt(7, u.getEstado());
            ps.setInt(8, u.getIdRol());
            ps.setInt(9, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al modificar usuario", e);
        }
        return false;
    }

    public boolean cambiarEstadoUsuario(int idUsuario, int nuevoEstado) {
        String sql = "UPDATE usuarios SET estado = ? WHERE idUsuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstado);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al cambiar estado del usuario", e);
        }
        return false;
    }

    public boolean eliminarUsuario(int idUsuario) {
        return cambiarEstadoUsuario(idUsuario, 2);
    }

    public Usuario obtenerUsuarioPorId(int idUsuario) {
        Usuario u = null;
        String sql = "SELECT u.*, r.nombre AS nombreRol FROM usuarios u " +
                     "LEFT JOIN roles r ON u.idRol = r.idRol " +
                     "WHERE u.idUsuario = ? AND u.estado <> 2";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellidos(rs.getString("apellidos"));
                    u.setDni(rs.getString("dni"));
                    u.setTelefono(rs.getString("telefono"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setEstado(rs.getInt("estado"));
                    u.setIdRol(rs.getInt("idRol"));

                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("idRol"));
                    rol.setNombre(rs.getString("nombreRol"));
                    u.setRol(rol);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener usuario por ID", e);
        }
        return u;
    }

    public boolean existeUsername(String username) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ? AND estado <> 2";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al verificar existencia de username", e);
        }
        return false;
    }

    public boolean existeDni(String dni) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE dni = ? AND estado <> 2";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al verificar existencia de DNI", e);
        }
        return false;
    }

    public Usuario obtenerUsuarioPorUsername(String username) {
        Usuario u = null;
        String sql = "SELECT u.*, r.nombre AS nombreRol FROM usuarios u " +
                     "LEFT JOIN roles r ON u.idRol = r.idRol " +
                     "WHERE u.username = ? AND u.estado = 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellidos(rs.getString("apellidos"));
                    u.setDni(rs.getString("dni"));
                    u.setTelefono(rs.getString("telefono"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setEstado(rs.getInt("estado"));
                    u.setIdRol(rs.getInt("idRol"));

                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("idRol"));
                    rol.setNombre(rs.getString("nombreRol"));
                    u.setRol(rol);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener usuario por username", e);
        }
        return u;
    }

    public Usuario obtenerUsuarioPorUsernameEstado(int estado, String username) {
        Usuario u = null;
        String sql = "SELECT u.*, r.nombre AS nombreRol FROM usuarios u " +
                     "LEFT JOIN roles r ON u.idRol = r.idRol " +
                     "WHERE u.username = ? AND u.estado = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, estado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellidos(rs.getString("apellidos"));
                    u.setDni(rs.getString("dni"));
                    u.setTelefono(rs.getString("telefono"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setEstado(rs.getInt("estado"));
                    u.setIdRol(rs.getInt("idRol"));

                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("idRol"));
                    rol.setNombre(rs.getString("nombreRol"));
                    u.setRol(rol);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener usuario por username y estado", e);
        }
        return u;
    }

    public boolean existeDniExceptoId(String dni, int idUsuario) {
    String sql = "SELECT COUNT(*) FROM usuarios WHERE dni = ? AND idUsuario <> ? AND estado <> 2";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, dni);
        ps.setInt(2, idUsuario);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        logger.error("Error al verificar existencia de DNI excluyendo idUsuario", e);
    }
    return false;
}
public boolean existeUsernameExceptoId(String username, int idUsuario) {
    String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ? AND idUsuario <> ? AND estado <> 2";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, username);
        ps.setInt(2, idUsuario);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        logger.error("Error al verificar existencia de username excluyendo idUsuario", e);
    }
    return false;
}

    public int contarUsuariosActivos() {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE estado = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarUsuariosInactivos() {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE estado = 0";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
