package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.common.base.Optional;
import dao.UsuarioDAO;
import modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UsuarioService;
import util.Seguridad;

public class UsuarioServiceTest {

    private UsuarioDAO usuarioDAOMock;
    private UsuarioService usuarioService;

    @BeforeEach
    public void setup() {
        usuarioDAOMock = mock(UsuarioDAO.class);
        usuarioService = new UsuarioService(usuarioDAOMock);
    }

    @Test
    public void testAutenticarUsuarioCorrectoEstado0() throws Exception {
        String username = "admin";
        String password = "admin123";
        String hashedPassword = Seguridad.hashSHA256(password);

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(hashedPassword);

        when(usuarioDAOMock.obtenerUsuarioPorUsernameEstado(0, username)).thenReturn(usuario);

        Optional<Usuario> resultado = usuarioService.autenticarUsuario(username, password);

        assertTrue(resultado.isPresent());
        assertEquals(username, resultado.get().getUsername());
    }

    @Test
    public void testAutenticarUsuarioIncorrecto() throws Exception {
        String username = "admin";
        String password = "admin123";

        when(usuarioDAOMock.obtenerUsuarioPorUsernameEstado(0, username)).thenReturn(null);
        when(usuarioDAOMock.obtenerUsuarioPorUsernameEstado(1, username)).thenReturn(null);

        Optional<Usuario> resultado = usuarioService.autenticarUsuario(username, password);

        assertFalse(resultado.isPresent());
    }

    @Test
    public void testValidarDuplicadosDni() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(5);
        usuario.setDni("12345678");
        usuario.setUsername("usuario5");

        when(usuarioDAOMock.existeDniExceptoId("12345678", 5)).thenReturn(true);

        String resultado = usuarioService.validarDuplicados(usuario);

        assertEquals("dni", resultado);
    }

    @Test
    public void testValidarDuplicadosUsername() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(5);
        usuario.setDni("12345678");
        usuario.setUsername("usuario5");

        when(usuarioDAOMock.existeUsernameExceptoId("usuario5", 5)).thenReturn(true);

        String resultado = usuarioService.validarDuplicados(usuario);

        assertEquals("username", resultado);
    }

    @Test
    public void testRegistrarUsuarioExitoso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsername("nuevo");
        usuario.setDni("11111111");
        usuario.setPassword("123");

        when(usuarioDAOMock.existeDni("11111111")).thenReturn(false);
        when(usuarioDAOMock.existeUsername("nuevo")).thenReturn(false);
        when(usuarioDAOMock.guardarUsuario(any(Usuario.class))).thenReturn(true);

        boolean resultado = usuarioService.registrarUsuario(usuario);

        assertTrue(resultado);
        verify(usuarioDAOMock).guardarUsuario(any(Usuario.class));
    }

    @Test
    public void testActualizarUsuarioConCambioDePassword() throws Exception {
        Usuario actual = new Usuario();
        actual.setIdUsuario(1);
        actual.setUsername("actualizado");
        actual.setPassword("nueva123");

        Usuario original = new Usuario();
        original.setIdUsuario(1);
        original.setUsername("actualizado");
        original.setPassword(Seguridad.hashSHA256("vieja123"));

        when(usuarioDAOMock.obtenerUsuarioPorId(1)).thenReturn(original);
        when(usuarioDAOMock.modificarUsuario(any(Usuario.class))).thenReturn(true);

        boolean resultado = usuarioService.actualizarUsuario(actual);

        assertTrue(resultado);
        verify(usuarioDAOMock).modificarUsuario(any(Usuario.class));
    }

    @Test
    public void testObtenerUsuarioPorIdExistente() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setUsername("admin");

        when(usuarioDAOMock.obtenerUsuarioPorId(1)).thenReturn(usuario);

        Usuario resultado = usuarioService.obtenerUsuarioPorId(1);

        assertNotNull(resultado);
        assertEquals("admin", resultado.getUsername());
    }

    @Test
    public void testObtenerUsuarioPorIdInexistente() {
        when(usuarioDAOMock.obtenerUsuarioPorId(999)).thenReturn(null);

        Usuario resultado = usuarioService.obtenerUsuarioPorId(999);

        assertNull(resultado);
    }
}
