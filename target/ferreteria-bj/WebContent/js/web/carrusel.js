document.addEventListener("DOMContentLoaded", () => {
  const slides = document.querySelectorAll(".slide");
  let index = 0;

  const showSlide = (i) => {
    slides.forEach(slide => slide.classList.remove("active"));
    slides[i].classList.add("active");
  };

  document.getElementById("prevBtn").addEventListener("click", () => {
    index = (index === 0) ? slides.length - 1 : index - 1;
    showSlide(index);
  });

  document.getElementById("nextBtn").addEventListener("click", () => {
    index = (index + 1) % slides.length;
    showSlide(index);
  });

  // AutoSlide opcional:
  setInterval(() => {
    index = (index + 1) % slides.length;
    showSlide(index);
  }, 5800);
});
