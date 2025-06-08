document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("modalRegistroCategoria");
    const btnAbrir = document.getElementById("btnAbrirModalCategoria");
    const btnCerrar = document.getElementById("cerrarModalRegistroCategoria");
    const btnCancelar = document.getElementById("btnCancelarRegistroCategoria");

    // Abrir el modal
    if (btnAbrir) {
        btnAbrir.addEventListener("click", function () {
            modal.style.display = "block";
        });
    }

    // Cerrar con la X
    if (btnCerrar) {
        btnCerrar.addEventListener("click", function () {
            modal.style.display = "none";
        });
    }

    // Cerrar con el botón Cancelar
    if (btnCancelar) {
        btnCancelar.addEventListener("click", function () {
            modal.style.display = "none";
        });
    }

    // Cerrar haciendo clic fuera del contenido del modal
    window.addEventListener("click", function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});
