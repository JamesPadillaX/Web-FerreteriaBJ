package service;

import dao.RolDAO;
import modelo.Rol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolService {
    private static final Logger logger = LoggerFactory.getLogger(RolService.class);
    private final RolDAO rolDAO = new RolDAO();

    public boolean registrarRol(String nombre) {
        Rol rol = new Rol();
        rol.setNombre(nombre);
        rol.setEstado(1);

        boolean resultado = rolDAO.guardarRol(rol);
        if (resultado) {
            logger.info("Rol registrado exitosamente: {}", nombre);
        } else {
            logger.error("No se pudo registrar el rol: {}", nombre);
        }

        return resultado;
    }

    public boolean rolExiste(String nombre) {
        boolean existe = rolDAO.existeRolPorNombre(nombre);
        if (existe) {
            logger.warn("Intento de registrar rol duplicado: {}", nombre);
        }
        return existe;
    }
}
