package SCM.School.Management.System.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import SCM.School.Management.System.model.StudentCourseEnrollment;
import java.util.List;

@Repository
public interface StudentCourseEnrollmentRepository extends JpaRepository<StudentCourseEnrollment,Long>{
    List<StudentCourseEnrollment> findByStudentId(Long studentId);
}