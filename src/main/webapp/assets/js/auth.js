// Bootstrap‑style client‑side validation
document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll(".needs-validation").forEach(form=>{
    form.addEventListener("submit",e=>{
      if(!form.checkValidity()){e.preventDefault();e.stopPropagation();}
      form.classList.add("was-validated");
    },false);
  });
});
