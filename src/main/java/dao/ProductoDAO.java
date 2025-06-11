package dao;

import modelo.Producto;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoDAO {

    private static final Logger logger = Logger.getLogger(ProductoDAO.class.getName());
    private Connection con;

    public ProductoDAO() {
        con = Conexion.getInstancia().getConexion();
    }

    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO productos (idCategoria, nombre, descripcion, precio, cantidad, estado, imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, producto.getIdCategoria());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getCantidad());
            ps.setInt(6, producto.getEstado());
            ps.setString(7, producto.getImagen());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al agregar producto", e);
            return false;
        }
    }

    public boolean editarProducto(Producto producto) {
        String sql = "UPDATE productos SET idCategoria = ?, nombre = ?, descripcion = ?, precio = ?, cantidad = ?, estado = ?, imagen = ? WHERE idProducto = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, producto.getIdCategoria());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getCantidad());
            ps.setInt(6, producto.getEstado());
            ps.setString(7, producto.getImagen());
            ps.setInt(8, producto.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al editar producto", e);
            return false;
        }
    }

    public boolean eliminarProducto(int idProducto) {
        String sql = "UPDATE productos SET estado = 2 WHERE idProducto = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar producto", e);
            return false;
        }
    }

    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS categoria FROM productos p " +
                     "LEFT JOIN categorias c ON p.idCategoria = c.idCategoria " +
                     "WHERE p.estado IN (0, 1)";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setIdCategoria(rs.getInt("idCategoria"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setEstado(rs.getInt("estado"));
                p.setCategoria(rs.getString("categoria"));
                p.setImagen(rs.getString("imagen"));
                lista.add(p);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar productos", e);
        }
        return lista;
    }

    public List<Producto> listarProductosPorCategoria(int idCategoria) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS categoria " +
                     "FROM productos p " +
                     "LEFT JOIN categorias c ON p.idCategoria = c.idCategoria " +
                     "WHERE p.idCategoria = ? AND p.estado IN (0, 1)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto();
                    p.setIdProducto(rs.getInt("idProducto"));
                    p.setIdCategoria(rs.getInt("idCategoria"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setCantidad(rs.getInt("cantidad"));
                    p.setEstado(rs.getInt("estado"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setImagen(rs.getString("imagen"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar productos por categoría", e);
        }
        return lista;
    }

    public List<Producto> listarProductosPorCategoriaWeb(int idCategoria) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS categoria " +
                     "FROM productos p " +
                     "LEFT JOIN categorias c ON p.idCategoria = c.idCategoria " +
                     "WHERE p.idCategoria = ? AND p.estado = 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto();
                    p.setIdProducto(rs.getInt("idProducto"));
                    p.setIdCategoria(rs.getInt("idCategoria"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setCantidad(rs.getInt("cantidad"));
                    p.setEstado(rs.getInt("estado"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setImagen(rs.getString("imagen"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar productos web por categoría", e);
        }
        return lista;
    }

    public Producto obtenerProductoPorId(int idProducto) {
        Producto p = null;
        String sql = "SELECT p.*, c.nombre AS categoria FROM productos p LEFT JOIN categorias c ON p.idCategoria = c.idCategoria WHERE p.idProducto = ? AND p.estado = 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Producto();
                    p.setIdProducto(rs.getInt("idProducto"));
                    p.setIdCategoria(rs.getInt("idCategoria"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setCantidad(rs.getInt("cantidad"));
                    p.setEstado(rs.getInt("estado"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setImagen(rs.getString("imagen"));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener producto por ID", e);
        }
        return p;
    }
}
