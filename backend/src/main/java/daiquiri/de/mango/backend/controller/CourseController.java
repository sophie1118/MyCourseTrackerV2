package daiquiri.de.mango.backend.controller;


import daiquiri.de.mango.backend.model.Course;
import daiquiri.de.mango.backend.services.CourseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping ("/api/courses")
public class CourseController {

    @Autowired
    CourseServices courseServices;

    @PostMapping("/addCourse")
    public ResponseEntity <?> addCourse (@Validated @RequestBody Course course, BindingResult result){
        if(result.hasErrors()){
            Map <String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());


            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }


        if (course.getCredits() == null || course.getName() == null || course.getPrerequisites() == null ){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Todos los campos son obligatorios.");

        }




        Course saved = this.courseServices.saveCourse(course);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);


    }


    @GetMapping ("/getByCode/{code}")
    public ResponseEntity<?> getByCode (@PathVariable String code){
        Optional<Course> course = this.courseServices.getByCode(code);

        if (course.isPresent()){
            return ResponseEntity.ok(course);


        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El curso con el codigo: "+code+ " no fue encontrado.");
    }

    @GetMapping ("/getAll")
    public ResponseEntity<?> getAllCourses () {
        List <Course> todos = this.courseServices.getAllCourses();

        if (todos.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No existe ningun curso registrado aun.");


        }

        return ResponseEntity.ok(todos);




    }

    @PutMapping ("/update/{code}")
    public ResponseEntity <?> update (@Validated @PathVariable String code, @RequestBody Course courseDets, BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);


        }

        Optional<Course> courseOp = this.courseServices.getByCode(code);
        if (courseOp.isPresent()){
            Course course = courseOp.get();
            course.setCredits(courseDets.getCredits());
            course.setName(courseDets.getName());
            course.setPrerequisites(courseDets.getPrerequisites());
            Course updated = this.courseServices.saveCourse(course);

            return ResponseEntity.ok(updated);





        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El curso con el codigo:" +code+ "No fue encontrado.");

    }


    @DeleteMapping("/delete/{code}")
    public ResponseEntity<?> delete (@PathVariable String code){
        Optional<Course> c = this.courseServices.getByCode(code);

        if (c.isPresent()){

            this.courseServices.deleteCourse(code);

            return ResponseEntity.ok("Curso eliminado correctamente.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El curso no fue encontrado.");


    }


}
