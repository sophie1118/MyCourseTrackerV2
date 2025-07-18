package daiquiri.de.mango.backend.repository;

import daiquiri.de.mango.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>  {
}
