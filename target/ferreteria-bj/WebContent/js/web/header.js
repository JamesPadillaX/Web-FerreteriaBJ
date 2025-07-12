document.addEventListener("DOMContentLoaded", () => {
  console.log("header.js cargado");

  const hamburgerBtn = document.getElementById("hamburgerBtn");
  const navMenu = document.getElementById("navMenu");

  if (hamburgerBtn && navMenu) {
    hamburgerBtn.addEventListener("click", () => {
      navMenu.classList.toggle("active");
    });
  }

  const userMenuBtn = document.getElementById("userMenuBtn");
  const userModal = document.getElementById("userModal");

  if (userMenuBtn && userModal) {
    userMenuBtn.addEventListener("click", (e) => {
      e.stopPropagation();

      const isVisible = userModal.style.display === "block";


      userModal.style.display = isVisible ? "none" : "block";

      if (!isVisible && navMenu && navMenu.classList.contains("active")) {
        navMenu.classList.remove("active");
      }
    });


    window.addEventListener("click", (e) => {
      const clickedOutside = !userModal.contains(e.target) && !userMenuBtn.contains(e.target);
      if (userModal.style.display === "block" && clickedOutside) {
        userModal.style.display = "none";
      }
    });
  }
});
