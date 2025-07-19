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
        console.log ("Estudiante creado:", data); //imprime los datos devueltos por el backend
    })

    //si hubo algun error:
    .catch((error)=>{ 
        console.error("Error:", error); //lo muestar en la  consola
        alert("Error al registrar al estudiante."); // Le avisa al usuario del problema



    })  


})