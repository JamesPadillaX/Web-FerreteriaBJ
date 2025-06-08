document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('modalEditarRol');
    const btnCerrarModal = document.getElementById('cerrarModalEditar');
    const btnCancelar = document.getElementById('btnCancelarEditarRol');
    const formEditar = document.getElementById('formEditarRol');

    function abrirModalEditarRol(id, nombre, estado) {
        document.getElementById('idRolEditar').value = id;
        document.getElementById('nombreEditar').value = nombre;
        document.getElementById('estadoEditar').value = estado;
        modal.style.display = 'block';
    }

    btnCerrarModal.addEventListener('click', () => {
        modal.style.display = 'none';
        formEditar.reset();
    });

    btnCancelar.addEventListener('click', () => {
        modal.style.display = 'none';
        formEditar.reset();
    });

    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.style.display = 'none';
            formEditar.reset();
        }
    });

    document.querySelectorAll('.btnEditarRol').forEach(btn => {
        btn.addEventListener('click', () => {
            const id = btn.getAttribute('data-id');
            const nombre = btn.getAttribute('data-nombre');
            const estado = btn.getAttribute('data-estado');
            abrirModalEditarRol(id, nombre, estado);
        });
    });
});
