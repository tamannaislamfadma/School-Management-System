package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // No need for custom queries here, JpaRepository provides the necessary CRUD operations.

    @Query("SELECT t FROM Teacher t WHERE t.activeStatus = 1")
    List<Teacher> findAllActiveTeachers();
}
