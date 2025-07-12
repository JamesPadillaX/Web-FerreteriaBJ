document.addEventListener("DOMContentLoaded", () => {
    const inputNombre = document.getElementById("nombre");
    const selectCategoria = document.getElementById("idCategoria");
    const formFiltro = document.getElementById("formFiltro");

    // Limpiar caracteres peligrosos al escribir
    const caracteresNoPermitidos = /[<>\/\\*!"#$%&()=+\[\]{}|]/g;

    inputNombre.addEventListener("input", function () {
        this.value = this.value.replace(caracteresNoPermitidos, '');
    });

    // Buscar al presionar Enter en el input de nombre
    inputNombre.addEventListener("keydown", function (e) {
        if (e.key === "Enter") {
            e.preventDefault(); // Evita que el formulario se envíe 2 veces
            formFiltro.submit();
        }
    });

    // Buscar al cambiar la categoría
    selectCategoria.addEventListener("change", function () {
        formFiltro.submit();
    });
});

// Limpiar filtros y recargar todo
function limpiarFiltros() {
    window.location.href = "ListarProductosServlet";
}
