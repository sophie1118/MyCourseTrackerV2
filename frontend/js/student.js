document.getElementById ("studentForm").addEventListener ("submit", function (event) {
    event.preventDefault();
    const carnet = document.getElementById ("carnet").value;
    const name = document.getElementById ("name").value;
    const email = document.getElementById ("email").value;

    //Se crea un objeto de tipo estudiante que almacena los datos enviados 
    const student = {
        carnet: carnet,
        name: name,
        email: email,
    };

    //peticion url para guardar el estudiante en la basde de datos
    fetch("http://localhost:8080/api/students/register", { //la url del metodo para a;adit un estudiante
        method: "POST", //endpoint parar registrar
        headers: { //se indica que el contenido a enviar es JSON
            "Content-Type": "application/json"
        },
        body: JSON.stringify(student), //se envía el objeto creado
        
    })

    //Cuando llega la respuesta del backend:
    .then((response)=> { //se ejecuta la funcion que se pasa como argumento
        if (!response.ok) { // si la repuesta no es ok 200
            throw new Error("Error al registrar al estudiante"); //se lanza un error
        }
        return response.json(); // si esta biem. convierte la respuesra en JSON para poder leerla y usalrla

    })
    //si todo sale bien:
    .then((data)=>{ //se ejecuta la funcion que se pasa como argumento
       alert("Estudiante registrado con exito.")//muestra un mensaje utilizando alert
       document.getElementById("studentForm").reset(); 
       console.log ("Estudiante creado:", data); //imprime los datos devueltos por el backend
       //limpia el formulario
    })

    //si hubo algun error:
    .catch((error)=>{ 
        console.error("Error:", error); //lo muestar en la  consola
        alert("Error al registrar al estudiante."); // Le avisa al usuario del problema



    })  



})


//funcion para obtener todos los estudiantes registrados
function mostrarEstudiantes (){
    fetch("http://localhost:8080/api/students/all") //direccion del endpoint donde get all
    .then((response)=>{ 
        if (!response.ok) { //si la respuessta no es Ok 200
            throw new Error("Error obteniendo los estudiantes"); //se lanza un error indicanco que no se pudieron obtener todos los estudiantes
        }
        return response.json(); //si esta bien. convierte la respuesra en JSON para poder leerla y usarla
    })
    //obetenemos el cuerpo de tabla en donde iran los estudiantes
    .then((data)=>{ 
        const tableBody = document.getElementById("studentTableBody");
        //limpiamos la tabla antes de agregar a los nuevs estudiantes
        tableBody.innerHTML = "";

        //recprremos la lista de estudiantes que mando el backend
        data.forEach((student)=>{
            //se crea una fila para la tabla
            const row = document.createElement("tr");
            
            //creamos una celda para el carnet
            const tdCarnet = document.createElement("td");
            tdCarnet.textContent = student.carnet;
            row.appendChild(tdCarnet);

            //creamos una celda para el nombre
            const tdName = document.createElement("td");
            tdName.textContent = student.name;
            row.appendChild(tdName);

            //creamos una celda para el correo
            const tdEmail = document.createElement("td");
            tdEmail.textContent = student.email;
            row.appendChild(tdEmail);

            //agregamos la fila a la tabla
            tableBody.appendChild(row);
        })
    })
    .catch((error)=>{
        console.error("Error obteniendo los estudiantes:", error);
        alert("Error obteniendo los estudiantes.");
    })


}

document.addEventListener("DOMContentLoaded", mostrarEstudiantes);