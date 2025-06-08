document.querySelectorAll('.btnEditarCategoria').forEach(btn => {
    btn.addEventListener('click', () => {
        const id = btn.dataset.id;
        const nombre = btn.dataset.nombre;
        const estado = btn.dataset.estado;

        document.getElementById('idCategoriaEditar').value = id;
        document.getElementById('nombreCategoriaEditar').value = nombre;
        document.getElementById('estadoCategoriaEditar').value = estado;

        document.getElementById('modalEditarCategoria').style.display = 'block';
    });
});

document.getElementById('cerrarModalEditarCategoria').addEventListener('click', () => {
    document.getElementById('modalEditarCategoria').style.display = 'none';
});

document.getElementById('btnCancelarEditarCategoria').addEventListener('click', () => {
    document.getElementById('modalEditarCategoria').style.display = 'none';
});
