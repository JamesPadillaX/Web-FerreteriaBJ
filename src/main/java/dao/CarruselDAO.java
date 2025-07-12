package dao;

import modelo.Carrusel;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarruselDAO {
    private Connection con;

    public CarruselDAO() {
        con = Conexion.getInstancia().getConexion();
    }


    public boolean registrar(Carrusel c) {
        String sql = "INSERT INTO carrusel (titulo, descripcion, rutaImagen, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDescripcion());
            ps.setString(3, c.getRutaImagen());
            ps.setInt(4, c.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public List<Carrusel> listarTodos() {
        List<Carrusel> lista = new ArrayList<>();
        String sql = "SELECT * FROM carrusel ORDER BY fechaRegistro ASC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Carrusel c = new Carrusel();
                c.setId(rs.getInt("idCarrusel"));
                c.setTitulo(rs.getString("titulo"));
                c.setDescripcion(rs.getString("descripcion"));
                c.setRutaImagen(rs.getString("rutaImagen"));
                c.setEstado(rs.getInt("estado"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // LISTAR SOLO ACTIVOS (para público)
    public List<Carrusel> listarActivos() {
        List<Carrusel> lista = new ArrayList<>();
        String sql = "SELECT * FROM carrusel WHERE estado = 1 ORDER BY fechaRegistro ASC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Carrusel c = new Carrusel();
                c.setId(rs.getInt("idCarrusel"));
                c.setTitulo(rs.getString("titulo"));
                c.setDescripcion(rs.getString("descripcion"));
                c.setRutaImagen(rs.getString("rutaImagen"));
                c.setEstado(rs.getInt("estado"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public Carrusel obtenerPorId(int id) {
        String sql = "SELECT * FROM carrusel WHERE idCarrusel = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Carrusel c = new Carrusel();
                    c.setId(rs.getInt("idCarrusel"));
                    c.setTitulo(rs.getString("titulo"));
                    c.setDescripcion(rs.getString("descripcion"));
                    c.setRutaImagen(rs.getString("rutaImagen"));
                    c.setEstado(rs.getInt("estado"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizar(Carrusel c) {
        String sql = "UPDATE carrusel SET titulo = ?, descripcion = ?, estado = ?" +
                     (c.getRutaImagen() != null ? ", rutaImagen = ?" : "") +
                     " WHERE idCarrusel = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getEstado());
            if (c.getRutaImagen() != null) {
                ps.setString(4, c.getRutaImagen());
                ps.setInt(5, c.getId());
            } else {
                ps.setInt(4, c.getId());
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

 
    public boolean eliminar(int idCarrusel) {
        String sql = "DELETE FROM carrusel WHERE idCarrusel = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCarrusel);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
