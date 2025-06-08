// Limpiar parámetros "error" y "correo" de la URL después de cargar la página
window.addEventListener('load', () => {
  if (window.history.replaceState) {
    const url = new URL(window.location);
    if (url.searchParams.has('error') || url.searchParams.has('correo')) {
      url.searchParams.delete('error');
      url.searchParams.delete('correo');
      window.history.replaceState(null, null, url.pathname);
    }
  }
});
