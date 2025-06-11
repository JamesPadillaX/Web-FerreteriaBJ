package service;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import dao.UsuarioDAO;
import modelo.Usuario;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    public Optional<Usuario> autenticarUsuario(String username, String password) {
        username = Strings.nullToEmpty(username).trim();
        password = Strings.nullToEmpty(password).trim();
        Usuario usuario = usuarioDAO.obtenerUsuarioPorUsernameEstado(0, username); // Estado 0 = inactivo
        if (usuario != null && usuario.getPassword().equals(password)) {
            return Optional.of(usuario); 
        }
        usuario = usuarioDAO.obtenerUsuarioPorUsernameEstado(1, username); // Estado 1 = activo
        if (usuario != null && usuario.getPassword().equals(password)) {
            return Optional.of(usuario); 
        }
        return Optional.absent(); 
    }

    public String validarDuplicados(Usuario usuario) {
        if (usuarioDAO.existeDniExceptoId(usuario.getDni(), usuario.getIdUsuario())) {
            return "dni";
        }
        if (usuarioDAO.existeUsernameExceptoId(usuario.getUsername(), usuario.getIdUsuario())) {
            return "username";
        }
        return ""; 
    }

    public boolean actualizarUsuario(Usuario usuario) {
        return usuarioDAO.modificarUsuario(usuario);
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioDAO.obtenerUsuarioPorId(id);
    }
}