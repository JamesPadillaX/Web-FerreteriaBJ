document.querySelectorAll('.btnEditarUsuario').forEach(button => {
  button.addEventListener('click', () => {
    // Mostrar modal
    const modal = document.getElementById('modalEditarUsuario');
    modal.style.display = 'flex';

    // Llenar campos con data-attributes del botón
    document.getElementById('editarIdUsuario').value = button.dataset.id;
    document.getElementById('editarNombre').value = button.dataset.nombre;
    document.getElementById('editarApellidos').value = button.dataset.apellidos;
    document.getElementById('editarDni').value = button.dataset.dni;
    document.getElementById('editarTelefono').value = button.dataset.telefono;
    document.getElementById('editarUsername').value = button.dataset.username;
    document.getElementById('editarPassword').value = button.dataset.password;
    document.getElementById('editarIdRol').value = button.dataset.rol;
    document.getElementById('editarEstado').value = button.dataset.estado;
  });
});

// Botón para cerrar el modal editar
document.getElementById('cerrarModalEditar').addEventListener('click', () => {
  document.getElementById('modalEditarUsuario').style.display = 'none';
});
document.getElementById('cancelarEditar').addEventListener('click', () => {
  document.getElementById('modalEditarUsuario').style.display = 'none';
});
