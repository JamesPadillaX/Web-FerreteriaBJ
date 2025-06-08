document.addEventListener("DOMContentLoaded", function () {
    console.log("JS cargado correctamente"); // Verifica en consola

    const modalProducto = document.getElementById("modalRegistroProducto");
    const btnAbrirModalProducto = document.getElementById("btnAbrirModalProducto");
    const btnCerrarModalProducto = document.getElementById("cerrarModalProducto");
    const btnCancelarRegistroProducto = document.getElementById("cancelarRegistroProducto");

    if (!modalProducto || !btnAbrirModalProducto) {
        console.error("No se encontró el modal o el botón de apertura");
        return;
    }

    btnAbrirModalProducto.onclick = () => {
        console.log("Mostrando modal");
        modalProducto.classList.add("show");
    };

    if (btnCerrarModalProducto) {
        btnCerrarModalProducto.onclick = () => {
            modalProducto.classList.remove("show");
        };
    }

    if (btnCancelarRegistroProducto) {
        btnCancelarRegistroProducto.onclick = () => {
            modalProducto.classList.remove("show");
        };
    }

    window.onclick = (event) => {
        if (event.target === modalProducto) {
            modalProducto.classList.remove("show");
        }
    };
});
