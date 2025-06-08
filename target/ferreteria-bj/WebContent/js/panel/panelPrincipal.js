document.addEventListener('DOMContentLoaded', () => {
    const menuLinks = document.querySelectorAll('.menu li a');
    const contentSection = document.getElementById('content-section');

    menuLinks.forEach(link => {
        const section = link.getAttribute('data-section');

        if (section) {
            // Solo agregamos el event listener si tiene data-section
            link.addEventListener('click', e => {
                e.preventDefault(); // prevenimos solo si es sección dinámica

                // Manejo visual del menú
                menuLinks.forEach(lnk => lnk.classList.remove('active'));
                link.classList.add('active');

                // Mostrar contenido simulado
                contentSection.innerHTML = `
                    <h1>${capitalize(section)}</h1>
                    <p>Contenido de la sección <strong>${capitalize(section)}</strong> está en construcción.</p>
                `;
            });
        }
    });

    function capitalize(text) {
        return text.charAt(0).toUpperCase() + text.slice(1);
    }
});
