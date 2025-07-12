document.addEventListener('DOMContentLoaded', () => {
    const inputBuscar = document.getElementById('inputBuscarCategoria');
    const filas = document.querySelectorAll('tbody tr[data-nombre]');
    const filaNoExiste = document.getElementById('filaNoExiste');

    const caracteresNoPermitidos = /[<>\/\\\*\!\@\#\$\%\^\&\(\)\=\+\[\]\{\}\|]/g;

    inputBuscar.addEventListener('input', function () {
        this.value = this.value.replace(caracteresNoPermitidos, '');

        const filtro = this.value.toLowerCase();
        let hayCoincidencia = false;

        filas.forEach(fila => {
            const nombre = fila.getAttribute('data-nombre').toLowerCase();
            const visible = nombre.includes(filtro);
            fila.style.display = visible ? '' : 'none';
            if (visible) hayCoincidencia = true;
        });

        if (filaNoExiste) {
            filaNoExiste.style.display = hayCoincidencia ? 'none' : '';
        }
    });
});

function limpiarBusqueda() {
    const input = document.getElementById('inputBuscarCategoria');
    input.value = '';
    const filas = document.querySelectorAll('tbody tr[data-nombre]');
    filas.forEach(fila => fila.style.display = '');

    const filaNoExiste = document.getElementById('filaNoExiste');
    if (filaNoExiste) {
        filaNoExiste.style.display = 'none';
    }
}
