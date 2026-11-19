package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Courses,Long> {
    List<Courses> findAllById(Long teacherId);
}
