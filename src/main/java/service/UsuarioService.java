package service;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import dao.UsuarioDAO;
import modelo.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Seguridad;

public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }


    public Optional<Usuario> autenticarUsuario(String username, String password) {
        username = Strings.nullToEmpty(username).trim();
        password = Strings.nullToEmpty(password).trim();

        try {
            String passwordHasheada = Seguridad.hashSHA256(password);

            Usuario usuario = usuarioDAO.obtenerUsuarioPorUsernameEstado(0, username);
            if (usuario != null && usuario.getPassword().equals(passwordHasheada)) {
                return Optional.of(usuario);
            }

        
            usuario = usuarioDAO.obtenerUsuarioPorUsernameEstado(1, username);
            if (usuario != null && usuario.getPassword().equals(passwordHasheada)) {
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

    /**
     * Registrar un nuevo usuario con validación y contraseña hasheada
     */
    public boolean registrarUsuario(Usuario usuario) {
        try {
            if (usuarioDAO.existeDni(usuario.getDni())) {
                logger.warn("Registro fallido: DNI ya existe -> {}", usuario.getDni());
                return false;
            }

            if (usuarioDAO.existeUsername(usuario.getUsername())) {
                logger.warn("Registro fallido: Username ya existe -> {}", usuario.getUsername());
                return false;
            }

            // Hashear la contraseña antes de guardar
            String password = usuario.getPassword();
            if (password != null && !password.isEmpty()) {
                usuario.setPassword(Seguridad.hashSHA256(password));
            } else {
                logger.warn("No se proporcionó una contraseña válida para registrar");
                return false;
            }

            boolean registrado = usuarioDAO.guardarUsuario(usuario);
            if (!registrado) {
                logger.warn("No se pudo registrar el usuario: {}", usuario.getUsername());
            }
            return registrado;
        } catch (Exception e) {
            logger.error("Error al registrar usuario: {}", usuario.getUsername(), e);
            return false;
        }
    }

    /**
     * Actualizar datos del usuario, hasheando contraseña solo si fue modificada
     */
    public boolean actualizarUsuario(Usuario usuario) {
        try {
            Usuario usuarioBD = usuarioDAO.obtenerUsuarioPorId(usuario.getIdUsuario());
            if (usuarioBD == null) {
                logger.warn("Usuario no encontrado para actualizar: ID {}", usuario.getIdUsuario());
                return false;
            }

            String nuevaContra = usuario.getPassword();
            String actualContra = usuarioBD.getPassword();

            // Hashear solo si la contraseña fue cambiada (compara con hash actual)
            if (!nuevaContra.equals(actualContra)) {
                usuario.setPassword(Seguridad.hashSHA256(nuevaContra));
            }

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

    /**
     * Obtener un usuario por ID
     */
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
