package dao;

import modelo.DomicilioCliente;
import util.Conexion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioClienteDAO {
    private static final Logger logger = LoggerFactory.getLogger(DomicilioClienteDAO.class);
    private Connection con;

    public DomicilioClienteDAO() {
        con = Conexion.getInstancia().getConexion();
    }

    public List<DomicilioCliente> listarPorCliente(int idCliente) {
        List<DomicilioCliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM domicilios_cliente WHERE idCliente = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DomicilioCliente d = new DomicilioCliente();
                    d.setIdDomicilio(rs.getInt("idDomicilio"));
                    d.setIdCliente(rs.getInt("idCliente"));
                    d.setCalle(rs.getString("calle"));
                    d.setNumero(rs.getString("numero"));
                    d.setDistrito(rs.getString("distrito"));
                    d.setProvincia(rs.getString("provincia"));
                    d.setDepartamento(rs.getString("departamento"));
                    d.setReferencia(rs.getString("referencia"));
                    d.setPrincipal(rs.getInt("principal") == 1);
                    lista.add(d);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al listar domicilios del cliente", e);
        }

        return lista;
    }

    public boolean guardar(DomicilioCliente domicilio) {
        String sql = "INSERT INTO domicilios_cliente (idCliente, calle, numero, distrito, provincia, departamento, referencia, principal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, domicilio.getIdCliente());
            ps.setString(2, domicilio.getCalle());
            ps.setString(3, domicilio.getNumero());
            ps.setString(4, domicilio.getDistrito());
            ps.setString(5, domicilio.getProvincia());
            ps.setString(6, domicilio.getDepartamento());
            ps.setString(7, domicilio.getReferencia());
            ps.setInt(8, domicilio.isPrincipal() ? 1 : 0);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al guardar domicilio del cliente", e);
        }
        return false;
    }

    public DomicilioCliente obtenerPorId(int idDomicilio) {
        String sql = "SELECT * FROM domicilios_cliente WHERE idDomicilio = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDomicilio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DomicilioCliente d = new DomicilioCliente();
                    d.setIdDomicilio(rs.getInt("idDomicilio"));
                    d.setIdCliente(rs.getInt("idCliente"));
                    d.setCalle(rs.getString("calle"));
                    d.setNumero(rs.getString("numero"));
                    d.setDistrito(rs.getString("distrito"));
                    d.setProvincia(rs.getString("provincia"));
                    d.setDepartamento(rs.getString("departamento"));
                    d.setReferencia(rs.getString("referencia"));
                    d.setPrincipal(rs.getInt("principal") == 1);
                    return d;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener domicilio por ID", e);
        }
        return null;
    }

    public boolean actualizar(DomicilioCliente domicilio) {
        String sql = "UPDATE domicilios_cliente SET calle=?, numero=?, distrito=?, provincia=?, departamento=?, referencia=?, principal=? WHERE idDomicilio=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, domicilio.getCalle());
            ps.setString(2, domicilio.getNumero());
            ps.setString(3, domicilio.getDistrito());
            ps.setString(4, domicilio.getProvincia());
            ps.setString(5, domicilio.getDepartamento());
            ps.setString(6, domicilio.getReferencia());
            ps.setInt(7, domicilio.isPrincipal() ? 1 : 0);
            ps.setInt(8, domicilio.getIdDomicilio());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al actualizar domicilio", e);
        }
        return false;
    }

    public boolean marcarComoPrincipal(int idCliente, int idDomicilio) {
        try {
            con.setAutoCommit(false);

            // Primero desmarcar todos
            String desmarcar = "UPDATE domicilios_cliente SET principal = 0 WHERE idCliente = ?";
            try (PreparedStatement ps = con.prepareStatement(desmarcar)) {
                ps.setInt(1, idCliente);
                ps.executeUpdate();
            }

            // Luego marcar el seleccionado
            String marcar = "UPDATE domicilios_cliente SET principal = 1 WHERE idDomicilio = ?";
            try (PreparedStatement ps = con.prepareStatement(marcar)) {
                ps.setInt(1, idDomicilio);
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            logger.error("Error al marcar domicilio como principal", e);
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                logger.error("Error en rollback", rollbackEx);
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                logger.error("Error al restaurar auto-commit", ex);
            }
        }
        return false;
    }

    public DomicilioCliente obtenerPrincipalPorCliente(int idCliente) {
    String sql = "SELECT * FROM domicilios_cliente WHERE idCliente = ? AND principal = 1 LIMIT 1";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idCliente);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                DomicilioCliente d = new DomicilioCliente();
                d.setIdDomicilio(rs.getInt("idDomicilio"));
                d.setCalle(rs.getString("calle"));
                d.setNumero(rs.getString("numero"));
                d.setDistrito(rs.getString("distrito"));
                d.setProvincia(rs.getString("provincia"));
                d.setDepartamento(rs.getString("departamento"));
                d.setReferencia(rs.getString("referencia"));
                d.setPrincipal(rs.getBoolean("principal"));
                return d;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

}
