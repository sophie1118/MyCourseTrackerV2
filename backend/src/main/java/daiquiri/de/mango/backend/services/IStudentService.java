package daiquiri.de.mango.backend.services;

import daiquiri.de.mango.backend.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    List<Student> getAllStudents();
    Optional<Student> getStudentByCarnet(String carnet);
    Student saveStudent(Student student);
    void deleteStudent(String carnet);
    Student enrollCourses(String carnet, List<String> courseCodes);

}
