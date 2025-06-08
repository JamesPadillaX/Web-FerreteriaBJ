const modal = document.getElementById('modalPermisos');

function abrirModal(button) {
    const idRol = button.getAttribute('data-idrol');
    fetch(`CargarPermisosRolServlet?idRol=${idRol}`)
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const contenidoModal = doc.querySelector('.modal-content');
            if (contenidoModal) {
                modal.innerHTML = '';
                modal.appendChild(contenidoModal);
                modal.style.display = 'flex';
            } else {
                alert('No se pudo cargar el contenido del modal');
            }
        })
        .catch(err => alert('Error cargando permisos: ' + err));
}

function cerrarModal() {
    modal.style.display = 'none';
    modal.innerHTML = '';
}

window.onclick = function(event) {
    if (event.target === modal) {
        cerrarModal();
    }
}

function enviarPermisos(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    fetch(form.action, {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(res => {
        if (res.trim() === 'OK') {
            alert('Permisos actualizados correctamente');
            cerrarModal();
        } else {
            alert('Error al guardar permisos: ' + res);
        }
    })
    .catch(err => alert('Error enviando formulario: ' + err));

    return false;
}
