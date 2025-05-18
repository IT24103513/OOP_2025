/* very light‑weight AOS alternative */
document.addEventListener("DOMContentLoaded", () => {
  const els = document.querySelectorAll("[data-aos]");
  const io  = new IntersectionObserver(entries=>{
      entries.forEach(e=>{
        if(e.isIntersecting){
          e.target.classList.add("aos-animate");
          io.unobserve(e.target);
        }
      });
  }, {threshold:.2});
  els.forEach(el=>io.observe(el));
});
