// Cambiar entre pestañas
document.getElementById("tabAgregar").addEventListener("click", () => {
  document.getElementById("seccionAgregar").style.display = "block";
  document.getElementById("seccionOpciones").style.display = "none";
});

document.getElementById("tabOpciones").addEventListener("click", () => {
  document.getElementById("seccionAgregar").style.display = "none";
  document.getElementById("seccionOpciones").style.display = "block";
  document.getElementById("panelOpciones").style.display = "none";
  document.getElementById("mensajeOpciones").textContent = "";
  document.getElementById("buscarCarnet").value = "";
});

// Funciones existentes para agregar y mostrar estudiantes
document.getElementById("studentForm").addEventListener("submit", function (event) {
  event.preventDefault();
  const carnet = document.getElementById("carnet").value.trim();
  const name = document.getElementById("name").value.trim();
  const email = document.getElementById("email").value.trim();

  const student = { carnet, name, email };

  fetch("http://localhost:8080/api/students/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(student),
  })
    .then((response) => {
      if (!response.ok) throw new Error("Error al registrar al estudiante");
      return response.json();
    })
    .then(() => {
      alert("Estudiante registrado con éxito");
      document.getElementById("studentForm").reset();
      mostrarEstudiantes();
    })
    .catch((error) => {
      alert(error.message);
    });
});

function mostrarEstudiantes() {
  fetch("http://localhost:8080/api/students/all")
    .then((res) => {
      if (!res.ok) throw new Error("Error al obtener estudiantes");
      return res.json();
    })
    .then((data) => {
      const tbody = document.getElementById("studentTableBody");
      tbody.innerHTML = "";
      data.forEach((student) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `<td>${student.carnet}</td><td>${student.name}</td><td>${student.email}</td>`;
        tbody.appendChild(tr);
      });
    })
    .catch((err) => alert(err.message));
}

// Buscar estudiante para opciones
document.getElementById("btnBuscar").addEventListener("click", () => {
  const carnet = document.getElementById("buscarCarnet").value.trim();
  if (!carnet) {
    alert("Ingrese un carnet para buscar");
    return;
  }

  fetch(`http://localhost:8080/api/students/${carnet}`)
    .then((res) => {
      if (!res.ok) throw new Error("Estudiante no encontrado");
      return res.json();
    })
    .then((student) => {
      document.getElementById("panelOpciones").style.display = "block";
      document.getElementById("editName").value = student.name;
      document.getElementById("editEmail").value = student.email;
      document.getElementById("mensajeOpciones").textContent = "";
    })
    .catch((err) => {
      document.getElementById("panelOpciones").style.display = "none";
      document.getElementById("mensajeOpciones").textContent = err.message;
    });
});

// Actualizar estudiante
document.getElementById("btnActualizar").addEventListener("click", () => {
  const carnet = document.getElementById("buscarCarnet").value.trim();
  const name = document.getElementById("editName").value.trim();
  const email = document.getElementById("editEmail").value.trim();

  fetch(`http://localhost:8080/api/students/update/${carnet}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, email }),
  })
    .then((res) => {
      if (!res.ok) throw new Error("Error al actualizar estudiante");
      return res.json();
    })
    .then(() => {
      document.getElementById("mensajeOpciones").textContent = "Estudiante actualizado con éxito";
      mostrarEstudiantes();
    })
    .catch((err) => {
      document.getElementById("mensajeOpciones").textContent = err.message;
    });
});

// Eliminar estudiante
document.getElementById("btnEliminar").addEventListener("click", () => {
  const carnet = document.getElementById("buscarCarnet").value.trim();
  if (!confirm("¿Seguro que desea eliminar este estudiante?")) return;

  fetch(`http://localhost:8080/api/students/delete/${carnet}`, {
    method: "DELETE",
  })
    .then((res) => {
      if (!res.ok) throw new Error("Error al eliminar estudiante");
      alert("Estudiante eliminado con éxito");
      mostrarEstudiantes();
      document.getElementById("panelOpciones").style.display = "none";
      document.getElementById("mensajeOpciones").textContent = "";
      document.getElementById("buscarCarnet").value = "";
    })
    .catch((err) => {
      document.getElementById("mensajeOpciones").textContent = err.message;
    });
});

// Mostrar estudiantes al cargar la página
document.addEventListener("DOMContentLoaded", mostrarEstudiantes);
