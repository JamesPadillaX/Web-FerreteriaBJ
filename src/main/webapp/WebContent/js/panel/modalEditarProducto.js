document.addEventListener("DOMContentLoaded", function () {
  const botonesEditar = document.querySelectorAll(".btnEditarProducto");

  botonesEditar.forEach(function (boton) {
    boton.addEventListener("click", function () {
      const producto = {
        idProducto: boton.getAttribute("data-id"),
        nombre: boton.getAttribute("data-nombre"),
        descripcion: boton.getAttribute("data-descripcion"),
        precio: boton.getAttribute("data-precio"),
        cantidad: boton.getAttribute("data-cantidad"),
        idCategoria: boton.getAttribute("data-idcategoria"),
        estado: boton.getAttribute("data-estado"),
        imagen: boton.getAttribute("data-imagen")
      };
      abrirModalEditar(producto);
    });
  });

  document.getElementById("cerrarModalEditarProducto").addEventListener("click", cerrarModalEditar);
  document.getElementById("cancelarEditarProducto").addEventListener("click", cerrarModalEditar);
});

function abrirModalEditar(producto) {
  // Resetear input file
  const inputImagen = document.getElementById("edit_imagen");
  inputImagen.value = "";

  // Vista previa de la imagen actual
  const preview = document.getElementById("previewImagenActual");
  preview.src = producto.imagen || "";

  // Llenar campos del formulario
  document.getElementById("edit_idProducto").value = producto.idProducto;
  document.getElementById("edit_nombre").value = producto.nombre;
  document.getElementById("edit_descripcion").value = producto.descripcion;
  document.getElementById("edit_precio").value = producto.precio;
  document.getElementById("edit_cantidad").value = producto.cantidad;
  document.getElementById("edit_idCategoria").value = producto.idCategoria;
  document.getElementById("edit_estado").value = producto.estado;
  document.getElementById("edit_imagenActual").value = producto.imagen;

  // Obtener filtros actuales desde el formulario de búsqueda
  const valorNombreFiltro = document.getElementById("nombre")?.value || "";
  const valorIdCategoriaFiltro = document.getElementById("idCategoria")?.value || "";

  // Guardar filtros actuales en los inputs ocultos del modal
  document.getElementById("edit_nombreFiltro").value = valorNombreFiltro;
  document.getElementById("edit_idCategoriaFiltro").value = valorIdCategoriaFiltro;

  // Mostrar el modal
  document.getElementById("modalEditarProducto").classList.add("show");
}

function cerrarModalEditar() {
  document.getElementById("modalEditarProducto").classList.remove("show");

  // Limpiar formulario y vista previa
  document.getElementById("formEditarProducto").reset();
  document.getElementById("previewImagenActual").src = "";
}
