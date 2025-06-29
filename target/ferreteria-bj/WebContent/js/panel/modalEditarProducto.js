document.addEventListener("DOMContentLoaded", function() {

  const botonesEditar = document.querySelectorAll(".btnEditarProducto");

  botonesEditar.forEach(function(boton) {
    boton.addEventListener("click", function() {
      const producto = {
        idProducto: boton.getAttribute("data-id"),
        nombre: boton.getAttribute("data-nombre"),
        descripcion: boton.getAttribute("data-descripcion"),
        precio: boton.getAttribute("data-precio"),
        cantidad: boton.getAttribute("data-cantidad"),
        idCategoria: boton.getAttribute("data-idcategoria"),
        estado: boton.getAttribute("data-estado"),
        imagen: boton.getAttribute("data-imagen") // ruta o URL de la imagen
      };
      abrirModalEditar(producto);
    });
  });


  document.getElementById("cerrarModalEditarProducto").addEventListener("click", cerrarModalEditar);

  // Cerrar modal con botón "Cancelar"
  document.getElementById("cancelarEditarProducto").addEventListener("click", cerrarModalEditar);

});
function abrirModalEditar(producto) {
  // Resetear input file para eliminar imagen previa seleccionada
  const inputImagen = document.getElementById("edit_imagen");
  inputImagen.value = ""; // limpia cualquier archivo anterior seleccionado

  // Resetear vista previa a la imagen original del producto
  const preview = document.getElementById("previewImagenActual");
  preview.src = producto.imagen || ""; // muestra la imagen original del producto

  // Llenar datos del producto
  document.getElementById("edit_idProducto").value = producto.idProducto;
  document.getElementById("edit_nombre").value = producto.nombre;
  document.getElementById("edit_descripcion").value = producto.descripcion;
  document.getElementById("edit_precio").value = producto.precio;
  document.getElementById("edit_cantidad").value = producto.cantidad;
  document.getElementById("edit_idCategoria").value = producto.idCategoria;
  document.getElementById("edit_estado").value = producto.estado;

  // Guardar ruta de imagen original
  document.getElementById("edit_imagenActual").value = producto.imagen;

  // Mostrar el modal
  document.getElementById("modalEditarProducto").classList.add("show");
}


function cerrarModalEditar() {
  document.getElementById("modalEditarProducto").classList.remove("show");

  // Limpiar formulario
  document.getElementById("formEditarProducto").reset();

  // Limpiar preview de imagen
  document.getElementById("previewImagenActual").src = "";
}
