// modalRegistroRol.js

document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('modalRegistroRol');
    const btnAbrirModal = document.getElementById('btnAbrirModalRol'); // botón para abrir modal (debes agregarlo en el JSP)
    const btnCerrarModal = document.getElementById('cerrarModalRegistro');
    const btnCancelar = document.getElementById('btnCancelarRegistroRol');
    const formRegistro = document.getElementById('formRegistroRol');

    btnAbrirModal?.addEventListener('click', () => {
        modal.style.display = 'block';
    });

    btnCerrarModal.addEventListener('click', () => {
        modal.style.display = 'none';
        formRegistro.reset();
    });

    btnCancelar.addEventListener('click', () => {
        modal.style.display = 'none';
        formRegistro.reset();
    });

    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.style.display = 'none';
            formRegistro.reset();
        }
    });
});
 