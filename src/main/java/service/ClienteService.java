package service;

import dao.ClienteDAO;
import modelo.Cliente;
import util.Seguridad;

public class ClienteService {
    private ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }
    
    public String registrarCliente(Cliente cliente, String passwordPlano) {
        if (clienteDAO.existeDni(cliente.getDni())) return "DNI_DUPLICADO";
        if (clienteDAO.existeCorreo(cliente.getCorreo())) return "CORREO_DUPLICADO";
        if (clienteDAO.existeTelefono(cliente.getTelefono())) return "TELEFONO_DUPLICADO";
    
        String passwordHash = Seguridad.hashSHA256(passwordPlano);
        cliente.setPassword(passwordHash);
        boolean registrado = clienteDAO.registrarCliente(cliente);
        return registrado ? "EXITO" : "ERROR";
    }

}
