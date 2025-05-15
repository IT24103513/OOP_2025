// Initialize AOS (Animate On Scroll)
document.addEventListener('DOMContentLoaded', () => {
  if (typeof AOS !== 'undefined') {
    AOS.init({
      duration: 600,
      once: true
    });
  }
});
