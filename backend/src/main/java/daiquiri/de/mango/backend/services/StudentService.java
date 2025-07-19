package daiquiri.de.mango.backend.services;

import daiquiri.de.mango.backend.model.Course;
import daiquiri.de.mango.backend.model.Student;
import daiquiri.de.mango.backend.repository.CourseRepository;
import daiquiri.de.mango.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentByCarnet(String carnet) {
        return studentRepository.findById(carnet);
    }

    @Override
    public Student saveStudent(Student student) {
        if (studentRepository.existsById(student.getCarnet())){
            throw new RuntimeException("Error: El carnet ya ha sido registrado.");
        }

        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(String carnet) {
        this.studentRepository.deleteById(carnet);
    }

    @Override
    public Student enrollCourses(String carnet, List<String> courseCodes) {
        Optional<Student> studentOpt = studentRepository.findById(carnet);
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Estudiante no encontrado");
        }
        Student student = studentOpt.get();

        List<Course> courses = courseRepository.findAllById(courseCodes);
        if (courses.size() != courseCodes.size()) {
            throw new RuntimeException("Algunos cursos no existen");
        }

        if (student.getCourses() == null) {
            student.setCourses(new ArrayList<>());
        }

        for (Course course : courses) {
            if (!student.getCourses().contains(course)) {
                student.getCourses().add(course);
            }
        }

        return studentRepository.save(student);
    }

    public Student updateStudent(String carnet, Student updatedData) {
        Student student = studentRepository.findById(carnet)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        student.setName(updatedData.getName());
        student.setEmail(updatedData.getEmail());
        // No toques el carnet porque es el ID
        return studentRepository.save(student);
    }
}
