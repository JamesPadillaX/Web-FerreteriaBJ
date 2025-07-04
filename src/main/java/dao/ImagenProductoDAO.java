package dao;

import modelo.ImagenProducto;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImagenProductoDAO {

    private Connection con;

    public ImagenProductoDAO() {
        con = Conexion.getInstancia().getConexion();
    }

    // Agregar imagen secundaria
    public boolean agregar(ImagenProducto img) {
        String sql = "INSERT INTO imagenes_producto (idProducto, ruta_imagen) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, img.getIdProducto());
            ps.setString(2, img.getRutaImagen());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Listar imágenes por producto
    public List<ImagenProducto> listarPorProducto(int idProducto) {
        List<ImagenProducto> lista = new ArrayList<>();
        String sql = "SELECT * FROM imagenes_producto WHERE idProducto = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ImagenProducto img = new ImagenProducto();
                img.setIdImagen(rs.getInt("idImagen"));
                img.setIdProducto(rs.getInt("idProducto"));
                img.setRutaImagen(rs.getString("ruta_imagen"));
                lista.add(img);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Eliminar una imagen
    public boolean eliminar(int idImagen) {
        String sql = "DELETE FROM imagenes_producto WHERE idImagen = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idImagen);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
