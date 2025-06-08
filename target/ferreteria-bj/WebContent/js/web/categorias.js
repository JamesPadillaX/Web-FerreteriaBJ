document.addEventListener("DOMContentLoaded", () => {
  const toggleBtn = document.getElementById("toggle-categorias");
  const dropdown = document.getElementById("categoriasDropdown");
  const container = document.getElementById("categorias-container");
  const blurOverlay = document.getElementById("blur-overlay");

  toggleBtn.addEventListener("click", (e) => {
    e.preventDefault();

    const rect = toggleBtn.getBoundingClientRect();
    dropdown.style.top = `${rect.bottom + window.scrollY + 8}px`; // +8px de separación

    dropdown.style.left = `${rect.left + window.scrollX}px`;

    const isVisible = dropdown.style.display === "block";

    if (isVisible) {
      dropdown.style.display = "none";
      blurOverlay.style.display = "none";
    } else {
      fetch("WebContent/componentes/categorias.jsp")
        .then(res => res.text())
        .then(html => {
          container.innerHTML = html;
          dropdown.style.display = "block";
          blurOverlay.style.display = "block";
        });
    }
  });

  // Cerrar al hacer clic fuera del dropdown o sobre el fondo difuminado
  window.addEventListener("click", function (e) {
    const target = e.target;
    if (!dropdown.contains(target) && target !== toggleBtn) {
      dropdown.style.display = "none";
      blurOverlay.style.display = "none";
    }
  });
});
