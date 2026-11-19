package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.ClassWiseSections;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


@Repository
public interface ClassWiseSectionRepository extends JpaRepository<ClassWiseSections,Long> {
//    List<ClassWiseSections> findByClassName(String className);


//    List<ClassWiseSections> findByClassesClassName(String className);
    @Query("SELECT cws FROM ClassWiseSections cws JOIN cws.classes c WHERE c.className = :className")
    List<ClassWiseSections> findClassesWiseSectionsByClassName(@Param("className") String className);

//    @Query("SELECT cws FROM ClassWiseSections cws JOIN FETCH cws.classes JOIN FETCH cws.sections")
//    List<ClassWiseSections> findAllWithClassesAndSections();

    Optional<ClassWiseSections> findByClassesIdAndSectionsSectionId(Long classId, Long sectionId);

    List<ClassWiseSections> findByClassesId(Long classId);

}
