document.addEventListener("DOMContentLoaded", function () {
  const userMenuBtn = document.getElementById("userMenuBtn");
  const userModal = document.getElementById("userModal");
  const modalContent = userModal.querySelector(".modal-content");

  if (userMenuBtn && userModal && modalContent) {
    userMenuBtn.addEventListener("click", function () {
      userModal.style.display = "block";
    });

    window.addEventListener("click", function (event) {
      if (userModal.style.display === "block" && !modalContent.contains(event.target) && event.target !== userMenuBtn) {
        userModal.style.display = "none";
      }
    });
  }
});
