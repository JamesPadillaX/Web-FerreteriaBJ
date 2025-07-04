<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="modalAgregarImagenes" class="modal-overlay" style="display: none;">
    <div class="modal-content">
        <span class="close-modal" id="btnCerrarModalAgregarImagenes">&times;</span>
        <h2>Agregar Imágenes al Producto</h2>

        <p style="text-align: center; margin-bottom: 10px; color: #555;">
            <span id="nombreCategoriaImagenes"></span>
        </p>

        <form action="SubirImagenesServlet" method="post" enctype="multipart/form-data" id="formAgregarImagenes">
            <input type="hidden" name="idProducto" id="idProductoImagenes">
            <input type="hidden" name="idCategoria" id="idCategoriaImagenes">

            <label for="imagenes">Seleccionar Imágenes (JPG, PNG):</label>
            <input type="file" name="imagenes" id="imagenes" accept="image/*" multiple required>

            <div class="modal-buttons">
                <button type="submit" class="btn-guardar">
                    <i class="fas fa-upload"></i> Subir Imágenes
                </button>
                <button type="button" class="btn-cancelar" id="btnCancelarAgregarImagenes">
                    Cancelar
                </button>
            </div>
        </form>
    </div>
</div>

<!-- Estilos del modal -->
<style>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(44, 62, 80, 0.7);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}

.modal-content {
    background-color: #fff;
    padding: 30px;
    border-radius: 16px;
    width: 90%;
    max-width: 500px;
    position: relative;
    box-shadow: 0 8px 24px rgba(0,0,0,0.2);
}

.modal-content h2 {
    margin-bottom: 20px;
    color: #2c3e50;
    text-align: center;
}

.modal-content label {
    font-weight: bold;
    display: block;
    margin-bottom: 8px;
}

.modal-content input[type="file"] {
    width: 100%;
    margin-bottom: 20px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 8px;
}

.modal-buttons {
    display: flex;
    justify-content: space-between;
    gap: 12px;
}

.btn-guardar {
    background-color: #6c63ff;
    color: white;
    border: none;
    padding: 10px 18px;
    font-size: 14px;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s ease;
}

.btn-guardar:hover {
    background-color: #574fd6;
}

.btn-cancelar {
    background-color: #ff4d4f;
    color: white;
    border: none;
    padding: 10px 18px;
    font-size: 14px;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s ease;
}

.btn-cancelar:hover {
    background-color: #d9363e;
}

.close-modal {
    position: absolute;
    top: 14px;
    right: 20px;
    font-size: 22px;
    color: #555;
    cursor: pointer;
}
</style>

<!-- Script para abrir/cerrar el modal y mostrar categoría -->
<script>
    document.querySelectorAll(".add-images").forEach(btn => {
        btn.addEventListener("click", () => {
            const idProducto = btn.dataset.id;
            const categoria = btn.dataset.categoria;
            const idCategoriaSeleccionada = document.getElementById("idCategoria")?.value || "";

            document.getElementById("idProductoImagenes").value = idProducto;
            document.getElementById("idCategoriaImagenes").value = idCategoriaSeleccionada;
            document.getElementById("nombreCategoriaImagenes").textContent = categoria;

            document.getElementById("modalAgregarImagenes").style.display = "flex";
        });
    });

    document.getElementById("btnCerrarModalAgregarImagenes").addEventListener("click", () => {
        cerrarModalAgregarImagenes();
    });

    document.getElementById("btnCancelarAgregarImagenes").addEventListener("click", () => {
        cerrarModalAgregarImagenes();
    });

    function cerrarModalAgregarImagenes() {
        document.getElementById("modalAgregarImagenes").style.display = "none";
        document.getElementById("formAgregarImagenes").reset();
        document.getElementById("nombreCategoriaImagenes").textContent = "";
    }
</script>
