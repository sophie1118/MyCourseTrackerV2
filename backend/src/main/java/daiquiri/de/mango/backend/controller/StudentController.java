package daiquiri.de.mango.backend.controller;

import daiquiri.de.mango.backend.model.Course;
import daiquiri.de.mango.backend.model.Student;
import daiquiri.de.mango.backend.repository.StudentRepository;
import daiquiri.de.mango.backend.services.StudentService;
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

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/students")
public class StudentController {


    @Autowired
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping ("/register")
    public ResponseEntity<?> registerStudent (@Validated @RequestBody Student student, BindingResult result){
        if (result.hasErrors()){
            Map <String, String> errors = new HashMap<>();


            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());


            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        if (student.getCarnet() == null || student.getEmail() == null || student.getName() == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El carnet, el nombre y el correo son campos obligatorios.");


        }

        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(savedStudent);


    }

    @GetMapping ("/{carnet}")
    public ResponseEntity<?> getByCarnet (@PathVariable String carnet ){
        Optional<Student> stu = this.studentService.getStudentByCarnet(carnet);

        if (stu.isPresent()){
            return ResponseEntity.ok(stu);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El estudiante no fue encontrado.");
    }

    @PostMapping("/{carnet}/enroll")
    public ResponseEntity<Student> enrollStudentInCourses(@PathVariable String carnet, @RequestBody List<String> courseCodes) {
        Student updatedStudent = studentService.enrollCourses(carnet, courseCodes);
        return ResponseEntity.ok(updatedStudent);
    }


    @DeleteMapping ("/delete/{carnet}")
    public ResponseEntity<?> delete (@PathVariable String carnet){
        Optional<Student> s = this.studentService.getStudentByCarnet(carnet);

        if (s.isPresent()){
            this.studentService.deleteStudent(carnet);

            return ResponseEntity.ok("Estudiante eliminado con exito.");


        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El estudiante no fue encontrado.");
    }



}
