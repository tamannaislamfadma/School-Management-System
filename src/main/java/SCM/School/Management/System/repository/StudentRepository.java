package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByName(String studentName);
    Optional<Student> findById(Long studentId);
}
