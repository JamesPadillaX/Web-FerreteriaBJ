package dao;

import modelo.ProductoWeb;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoWebDAO {

    private static final Logger logger = Logger.getLogger(ProductoWebDAO.class.getName());
    private Connection con;

    public ProductoWebDAO() {
        con = Conexion.getInstancia().getConexion();
    }

    /**
     * Obtiene los productos más vendidos para mostrar en la web (vista vista_productos_mas_vendidos_web)
     */
    public List<ProductoWeb> obtenerMasVendidos(int limite) {
        List<ProductoWeb> lista = new ArrayList<>();
        String sql = "SELECT * FROM vista_productos_mas_vendidos_web ORDER BY total_vendido DESC LIMIT ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductoWeb prod = new ProductoWeb();
                    prod.setIdProducto(rs.getInt("idProducto"));
                    prod.setNombre(rs.getString("nombre"));
                    prod.setDescripcion(rs.getString("descripcion"));
                    prod.setPrecio(rs.getBigDecimal("precio"));
                    prod.setImagen(rs.getString("imagen"));
                    prod.setCategoria(rs.getString("categoria"));
                    prod.setTotalVendido(rs.getInt("total_vendido"));
                    lista.add(prod);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener productos más vendidos (web)", e);
        }

        return lista;
    }

    /**
     * Obtiene todos los productos activos para la vista pública
     */
    public List<ProductoWeb> obtenerTodos() {
        List<ProductoWeb> lista = new ArrayList<>();
        String sql = "SELECT p.idProducto, p.nombre, p.descripcion, p.precio, p.imagen, c.nombre AS categoria " +
                     "FROM productos p " +
                     "LEFT JOIN categorias c ON p.idCategoria = c.idCategoria " +
                     "WHERE p.estado = 1";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductoWeb prod = new ProductoWeb();
                prod.setIdProducto(rs.getInt("idProducto"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setPrecio(rs.getBigDecimal("precio"));
                prod.setImagen(rs.getString("imagen"));
                prod.setCategoria(rs.getString("categoria"));
                lista.add(prod);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener todos los productos (web)", e);
        }

        return lista;
    }

    /**
 * Busca productos activos por nombre (para la vista pública)
 */
public List<ProductoWeb> buscarProductosActivosPorNombre(String query) {
    List<ProductoWeb> lista = new ArrayList<>();
    String sql = "SELECT p.idProducto, p.nombre, p.descripcion, p.precio, p.imagen, c.nombre AS categoria " +
                 "FROM productos p " +
                 "LEFT JOIN categorias c ON p.idCategoria = c.idCategoria " +
                 "WHERE p.estado = 1 AND p.nombre LIKE ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, "%" + query + "%");

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductoWeb prod = new ProductoWeb();
                prod.setIdProducto(rs.getInt("idProducto"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setPrecio(rs.getBigDecimal("precio"));
                prod.setImagen(rs.getString("imagen"));
                prod.setCategoria(rs.getString("categoria"));
                lista.add(prod);
            }
        }

    } catch (SQLException e) {
        logger.log(Level.SEVERE, "Error al buscar productos activos por nombre (web)", e);
    }

    return lista;
}
public List<ProductoWeb> buscarPorNombre(String nombre) {
    List<ProductoWeb> lista = new ArrayList<>();
    String sql = "SELECT p.idProducto, p.nombre, p.descripcion, p.precio, p.imagen, c.nombre AS categoria " +
                 "FROM productos p " +
                 "LEFT JOIN categorias c ON p.idCategoria = c.idCategoria " +
                 "WHERE p.estado = 1 AND p.nombre LIKE ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, "%" + nombre + "%");
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductoWeb prod = new ProductoWeb();
                prod.setIdProducto(rs.getInt("idProducto"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setPrecio(rs.getBigDecimal("precio"));
                prod.setImagen(rs.getString("imagen"));
                prod.setCategoria(rs.getString("categoria"));
                lista.add(prod);
            }
        }
    } catch (SQLException e) {
        logger.log(Level.SEVERE, "Error al buscar productos por nombre", e);
    }

    return lista;
}

}
