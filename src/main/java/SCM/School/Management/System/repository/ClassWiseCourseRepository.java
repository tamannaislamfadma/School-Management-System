package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.ClassWiseCourse;
import SCM.School.Management.System.model.ClassWiseSections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassWiseCourseRepository extends JpaRepository<ClassWiseCourse,Long> {
    @Query("SELECT cwc FROM ClassWiseCourse cwc JOIN cwc.classes c WHERE LOWER(c.className) = LOWER(:className)")
    List<ClassWiseCourse> findClassesWiseCoursesByClassName(@Param("className") String className);


//      Optional<ClassWiseSections> findByClassesIdAndSectionsSectionId(Long classId, Long sectionId);

    Optional<ClassWiseCourse>findByClassesIdAndCoursesId(Long classId, Long courseId);
}
