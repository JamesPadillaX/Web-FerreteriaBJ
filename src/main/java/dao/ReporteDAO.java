package dao;

import modelo.ProductoMasVendido;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReporteDAO {

    private static final Logger logger = Logger.getLogger(ReporteDAO.class.getName());
    private Connection con;

    public ReporteDAO() {
        con = Conexion.getInstancia().getConexion();
    }

    public List<ProductoMasVendido> obtenerProductosMasVendidos() {
        List<ProductoMasVendido> lista = new ArrayList<>();
        String sql = "SELECT * FROM vista_productos_mas_vendidos ORDER BY total_vendido DESC LIMIT 10";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductoMasVendido pmv = new ProductoMasVendido();
                pmv.setIdProducto(rs.getInt("idProducto"));
                pmv.setNombre(rs.getString("producto"));
                pmv.setCategoria(rs.getString("categoria"));
                pmv.setTotalVendido(rs.getInt("total_vendido"));
                pmv.setMontoGenerado(rs.getDouble("monto_generado"));
                lista.add(pmv);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener productos más vendidos desde la vista", e);
        }

        return lista;
    }
}
