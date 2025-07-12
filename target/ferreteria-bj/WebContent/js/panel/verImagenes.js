document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".btnVerImagenes").forEach(btn => {
        btn.addEventListener("click", () => {
            const nombre = btn.getAttribute("data-nombre");
            const dataImagenes = btn.getAttribute("data-imagenes") || "[]";
            let imagenes = [];

            try {
                imagenes = JSON.parse(dataImagenes);
            } catch (e) {
                console.error("Error al parsear imágenes:", dataImagenes, e);
            }

            document.getElementById("nombreProductoVer").textContent = nombre;
            const contenedor = document.getElementById("contenedorImagenes");
            contenedor.innerHTML = "";

            if (imagenes.length === 0) {
                contenedor.innerHTML = "<div style='text-align:center; color:#999;'>No tiene imágenes secundarias.</div>";
            } else {
                imagenes.forEach(src => {
                    const img = document.createElement("img");
                    img.src = src;
                    img.alt = "Imagen producto";
                    contenedor.appendChild(img);
                });
            }

            document.getElementById("modalVerImagenes").style.display = "flex";
        });
    });

    document.getElementById("btnCerrarModalVerImagenes").addEventListener("click", () => {
        document.getElementById("modalVerImagenes").style.display = "none";
    });

    document.getElementById("btnCancelarVerImagenes").addEventListener("click", () => {
        document.getElementById("modalVerImagenes").style.display = "none";
    });
});
