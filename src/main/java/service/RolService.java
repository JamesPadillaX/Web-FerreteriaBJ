package service;

import dao.RolDAO;
import modelo.Rol;

public class RolService {

    private RolDAO rolDAO;

    public RolService() {
        rolDAO = new RolDAO();
    }

    public boolean rolExiste(String nombre) {
        return rolDAO.existeRolPorNombre(nombre);
    }

    public boolean registrarRol(String nombre) {
        Rol rol = new Rol();
        rol.setNombre(nombre);
        rol.setEstado(1); // Activo por defecto
        return rolDAO.guardarRol(rol);
    }
}
