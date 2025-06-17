package service;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import dao.UsuarioDAO;
import modelo.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarioService {
    
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Constructor para pruebas (inyecta el mock)
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Optional<Usuario> autenticarUsuario(String username, String password) {
        username = Strings.nullToEmpty(username).trim();
        password = Strings.nullToEmpty(password).trim();

        try {
            Usuario usuario = usuarioDAO.obtenerUsuarioPorUsernameEstado(0, username);
            if (usuario != null && usuario.getPassword().equals(password)) {
                return Optional.of(usuario);
            }

            usuario = usuarioDAO.obtenerUsuarioPorUsernameEstado(1, username);
            if (usuario != null && usuario.getPassword().equals(password)) {
                return Optional.of(usuario);
            }

            logger.warn("Fallo en autenticación para usuario: {}", username);
        } catch (Exception e) {
            logger.error("Error durante la autenticación del usuario: {}", username, e);
        }

        return Optional.absent();
    }

    public String validarDuplicados(Usuario usuario) {
        try {
            if (usuarioDAO.existeDniExceptoId(usuario.getDni(), usuario.getIdUsuario())) {
                logger.warn("DNI duplicado detectado: {}", usuario.getDni());
                return "dni";
            }

            if (usuarioDAO.existeUsernameExceptoId(usuario.getUsername(), usuario.getIdUsuario())) {
                logger.warn("Username duplicado detectado: {}", usuario.getUsername());
                return "username";
            }
        } catch (Exception e) {
            logger.error("Error al validar duplicados para usuario: {}", usuario.getUsername(), e);
        }
        return "";
    }

    public boolean actualizarUsuario(Usuario usuario) {
        try {
            boolean actualizado = usuarioDAO.modificarUsuario(usuario);
            if (!actualizado) {
                logger.warn("No se pudo actualizar el usuario: {}", usuario.getUsername());
            }
            return actualizado;
        } catch (Exception e) {
            logger.error("Error al actualizar usuario: {}", usuario.getUsername(), e);
            return false;
        }
    }

    public Usuario obtenerUsuarioPorId(int id) {
        try {
            Usuario usuario = usuarioDAO.obtenerUsuarioPorId(id);
            if (usuario == null) {
                logger.warn("No se encontró usuario con ID: {}", id);
            }
            return usuario;
        } catch (Exception e) {
            logger.error("Error al obtener usuario por ID: {}", id, e);
            return null;
        }
    }
}
