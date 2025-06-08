package dao;

import modelo.Categoria;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private Connection con;

    public CategoriaDAO() {
        con = Conexion.getInstancia().getConexion();
    }


    public List<Categoria> listarCategorias() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias WHERE estado <> 2 ORDER BY idCategoria";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setEstado(rs.getInt("estado"));
                lista.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean guardarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categorias(nombre, estado) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setInt(2, 1); 
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Categoria obtenerCategoriaPorId(int idCategoria) {
        Categoria categoria = null;
        String sql = "SELECT * FROM categorias WHERE idCategoria = ? AND estado <> 2";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    categoria = new Categoria();
                    categoria.setIdCategoria(rs.getInt("idCategoria"));
                    categoria.setNombre(rs.getString("nombre"));
                    categoria.setEstado(rs.getInt("estado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoria;
    }


    public boolean modificarCategoria(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ?, estado = ? WHERE idCategoria = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getEstado());
            ps.setInt(3, categoria.getIdCategoria());
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean cambiarEstadoCategoria(int idCategoria, int nuevoEstado) {
        String sql = "UPDATE categorias SET estado = ? WHERE idCategoria = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstado);
            ps.setInt(2, idCategoria);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean eliminarCategoria(int idCategoria) {
        return cambiarEstadoCategoria(idCategoria, 2);
    }


    public List<Categoria> listarCategoriasActivas() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias WHERE estado = 1 ORDER BY idCategoria";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setEstado(rs.getInt("estado"));
                lista.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
