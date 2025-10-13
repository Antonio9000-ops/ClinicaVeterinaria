function abrirModalCuenta() {
  const usuario = JSON.parse(localStorage.getItem("usuario")) || {};
  document.getElementById("nombre").value = usuario.nombre || "";
  document.getElementById("apellido").value = usuario.apellido || "";
  document.getElementById("telefono").value = usuario.telefono || "";
  document.getElementById("direccion").value = usuario.direccion || "";

  const modal = new bootstrap.Modal(document.getElementById("modalCuenta"));
  modal.show();
}

function guardarCambios() {
  const usuario = {
    nombre: document.getElementById("nombre").value,
    apellido: document.getElementById("apellido").value,
    telefono: document.getElementById("telefono").value,
    direccion: document.getElementById("direccion").value,
  };

  localStorage.setItem("usuario", JSON.stringify(usuario));
  alert("✅ Datos actualizados correctamente.");
  bootstrap.Modal.getInstance(document.getElementById("modalCuenta")).hide();
}

function logout() {
  localStorage.removeItem("usuario");
  alert("👋 Sesión cerrada.");
  setTimeout(() => (window.location.href = "/login"), 1000);
}
