package service;

import dao.ClienteDAO;
import modelo.Cliente;
import util.Seguridad;

public class ClienteService {
    private ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    // Método para registrar cliente con validaciones y hash
    public boolean registrarCliente(Cliente cliente, String passwordPlano) {
        if (clienteDAO.existeDni(cliente.getDni())) return false;
        if (clienteDAO.existeCorreo(cliente.getCorreo())) return false;
        if (clienteDAO.existeTelefono(cliente.getTelefono())) return false;

        // Hashear la contraseña
        String passwordHash = Seguridad.hashSHA256(passwordPlano);
        cliente.setPassword(passwordHash);

        return clienteDAO.registrarCliente(cliente);
    }

}
