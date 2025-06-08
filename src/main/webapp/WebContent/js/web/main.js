document.addEventListener('DOMContentLoaded', () => {
  const toggleCategorias = document.getElementById('toggle-categorias');
  const categorias = document.querySelector('.categorias');

  if (toggleCategorias && categorias) {
    toggleCategorias.addEventListener('click', (e) => {
      e.preventDefault();
      categorias.classList.toggle('visible');
    });
  }
});
