window.addEventListener("DOMContentLoaded", () => {
    const errorMsg = document.querySelector(".error-msg");
    if (errorMsg) {
        setTimeout(() => {
            errorMsg.classList.add("fade-out");
            setTimeout(() => errorMsg.remove(), 1000); // Eliminar del DOM tras animación
        }, 3000); // Espera 3 segundos antes de empezar a desvanecer
    }
});
