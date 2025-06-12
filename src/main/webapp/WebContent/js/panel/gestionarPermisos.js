function abrirModal(button) {
    const idRol = button.getAttribute("data-idrol");
    const nombreRol = button.getAttribute("data-nombrerol");

    fetch(`CargarPermisosRolServlet?idRol=${idRol}&nombreRol=${encodeURIComponent(nombreRol)}`)
        .then(response => response.text())
        .then(html => {
            const modal = document.getElementById("modalPermisos");
            modal.querySelector(".modal-content").innerHTML = html;
            modal.classList.add("active"); // ✅ Aplica correctamente el estilo del modal con fondo y animación

        })
        .catch(error => {
            console.error("Error al cargar el modal:", error);
        });
}

function cerrarModal() {
    const modal = document.getElementById("modalPermisos");
    modal.classList.remove("active");
}


window.onclick = function(event) {
    const modal = document.getElementById("modalPermisos");
    if (event.target === modal) {
        cerrarModal();
    }
};
