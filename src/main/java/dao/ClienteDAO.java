package dao;

import modelo.Cliente;
import util.Conexion;
import util.Seguridad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection con;

    public ClienteDAO() {
        con = Conexion.getInstancia().getConexion();
    }

    public boolean registrarCliente(Cliente c) {
        String sql = "INSERT INTO clientes(nombre, apellidos, dni, telefono, correo, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidos());
            ps.setString(3, c.getDni());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCorreo());
            ps.setString(6, c.getPassword()); 
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cliente validarLogin(String correo, String passwordPlano) {
        String sql = "SELECT * FROM clientes WHERE correo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashGuardado = rs.getString("password");
                    String hashIngresado = Seguridad.hashSHA256(passwordPlano);
                    if (hashGuardado.equals(hashIngresado)) { 
                        Cliente c = new Cliente();
                        c.setIdCliente(rs.getInt("idCliente"));
                        c.setNombre(rs.getString("nombre"));
                        c.setApellidos(rs.getString("apellidos"));
                        c.setDni(rs.getString("dni"));
                        c.setTelefono(rs.getString("telefono"));
                        c.setCorreo(rs.getString("correo"));
                        return c;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellidos(rs.getString("apellidos"));
                c.setDni(rs.getString("dni"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                c.setPassword(rs.getString("password"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizarCliente(Cliente c) {
        String sql = "UPDATE clientes SET nombre = ?, apellidos = ?, dni = ?, telefono = ?, correo = ?, password = ? WHERE idCliente = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidos());
            ps.setString(3, c.getDni());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCorreo());
            ps.setString(6, c.getPassword());
            ps.setInt(7, c.getIdCliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cliente obtenerClientePorId(int idCliente) {
        String sql = "SELECT * FROM clientes WHERE idCliente = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setIdCliente(rs.getInt("idCliente"));
                    c.setNombre(rs.getString("nombre"));
                    c.setApellidos(rs.getString("apellidos"));
                    c.setDni(rs.getString("dni"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setCorreo(rs.getString("correo"));
                    c.setPassword(rs.getString("password"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean existeDni(String dni) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE dni = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean existeCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE correo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean existeTelefono(String telefono) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE telefono = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, telefono);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Cliente obtenerClientePorCorreo(String correo) {
    String sql = "SELECT * FROM clientes WHERE correo = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, correo);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellidos(rs.getString("apellidos"));
                c.setDni(rs.getString("dni"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                c.setPassword(rs.getString("password"));
                return c;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

}
