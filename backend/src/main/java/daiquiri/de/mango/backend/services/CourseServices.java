package daiquiri.de.mango.backend.services;

import daiquiri.de.mango.backend.model.Course;
import daiquiri.de.mango.backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServices implements ICourseServices{

    @Autowired
    CourseRepository courseRepository;


    @Override
    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    @Override
    public Optional<Course> getByCode(String code) {
        return this.courseRepository.findById(code);
    }


    @Override
    public Course saveCourse(Course course) {
        return this.courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String code) {
        this.courseRepository.deleteById(code);
    }
}
