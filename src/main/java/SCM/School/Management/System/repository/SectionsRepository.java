package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.Sections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionsRepository extends JpaRepository<Sections,Long> {

    List<Sections> findByClassesId(Long classId);


}
