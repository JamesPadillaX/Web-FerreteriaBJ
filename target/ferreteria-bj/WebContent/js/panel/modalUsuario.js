document.getElementById("btnAbrirModal").addEventListener("click", function () {
    document.getElementById("modalRegistroUsuario").style.display = "flex";
});

document.getElementById("cerrarModalRegistro").addEventListener("click", function () {
    document.getElementById("modalRegistroUsuario").style.display = "none";
});

document.getElementById("cancelarRegistro").addEventListener("click", function () {
    document.getElementById("modalRegistroUsuario").style.display = "none";
});

window.addEventListener("click", function (event) {
    const modal = document.getElementById("modalRegistroUsuario");
    if (event.target === modal) {
        modal.style.display = "none";
    }
});
