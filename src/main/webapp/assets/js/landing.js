// initialize AOS
AOS.init({
  duration: 800,
  once: true
});

// change navbar background on scroll
const nav = document.querySelector('.navbar-transparent');
window.addEventListener('scroll', () => {
  if (window.scrollY > 50) {
    nav.classList.add('scrolled');
  } else {
    nav.classList.remove('scrolled');
  }
});
