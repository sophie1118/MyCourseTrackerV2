package daiquiri.de.mango.backend.services;

import daiquiri.de.mango.backend.model.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseServices {
    List<Course> getAllCourses();
    Optional<Course> getByCode (String code);
    Course saveCourse (Course course);
    void deleteCourse (String code);




}
