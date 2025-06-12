package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.common.base.Optional;
import org.junit.jupiter.api.Test;

import dao.UsuarioDAO;
import modelo.Usuario;
import service.UsuarioService;

public class UsuarioServiceTest {

    @Test
    public void autenticarUsuario_DatosCorrectos_RetornaUsuario() {
        UsuarioDAO mockDAO = mock(UsuarioDAO.class);
        Usuario usuario = new Usuario();
        usuario.setUsername("admin");
        usuario.setPassword("123");

        when(mockDAO.obtenerUsuarioPorUsernameEstado(0, "admin")).thenReturn(null);
        when(mockDAO.obtenerUsuarioPorUsernameEstado(1, "admin")).thenReturn(usuario);

        UsuarioService service = new UsuarioService(mockDAO);

        Optional<Usuario> resultado = service.autenticarUsuario("admin", "123");

        assertTrue(resultado.isPresent());
        assertEquals("admin", resultado.get().getUsername());
    }
}
