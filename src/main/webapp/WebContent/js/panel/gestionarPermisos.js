
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

                // Refuerza el valor del input hidden
                const inputHidden = modal.querySelector('input[name="idRol"]');
                if (inputHidden) {
                    inputHidden.value = idRol;
                    console.log("✅ idRol cargado correctamente:", idRol);
                }
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

    const idRol = form.querySelector('input[name="idRol"]')?.value;
    const permisosSeleccionados = form.querySelectorAll('input[name="permisos"]:checked');

    const formData = new URLSearchParams();

    if (idRol) {
        formData.append("idRol", idRol);
    }

    permisosSeleccionados.forEach(p => {
        formData.append("permisos", p.value);
    });

    console.log("📤 Enviando idRol:", idRol);
    console.log("📤 Enviando permisos:", [...permisosSeleccionados].map(p => p.value));

    fetch(form.action, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData.toString()
    })
    .then(response => {
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return response.text();
    })
    .then(res => {
        if (res.trim() === 'OK') {
            alert("✅ Permisos actualizados correctamente");
            cerrarModal();
        } else {
            alert("❌ Error al guardar permisos: " + res);
        }
    })
    .catch(err => {
        console.error("❌ Error:", err);
        alert("❌ Error enviando datos: " + err);
    });
}
