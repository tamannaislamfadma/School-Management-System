package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.ClassWiseSections;
import SCM.School.Management.System.model.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {
    Routine findByClassNameAndSectionName(String className, String sectionName);

    Optional<Routine> findById(Long id);

}
