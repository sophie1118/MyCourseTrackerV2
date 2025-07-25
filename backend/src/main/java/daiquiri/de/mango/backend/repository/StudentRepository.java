package daiquiri.de.mango.backend.repository;

import daiquiri.de.mango.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository <Student, String> {
    public Student findByCarnet (String carnet);

}
