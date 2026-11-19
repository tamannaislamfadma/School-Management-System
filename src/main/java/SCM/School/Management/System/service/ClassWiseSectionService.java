package SCM.School.Management.System.service;


import SCM.School.Management.System.model.ClassWiseSections;
import SCM.School.Management.System.model.Classes;
import SCM.School.Management.System.model.Sections;
import java.util.List;

public interface ClassWiseSectionService {
    List<Classes> getAllClasses();

    List<Sections> getAllSections();

    List<ClassWiseSections> getAllClassWiseSections();

    void saveClass(Classes classes);

    void saveSections(Sections sections);

    void saveClassWiseSections(Long classId, Long sectionId);

    List<ClassWiseSections> getAllClassWiseSectionByClassName(String className);

    void deleteClassSectionById(Long id);
    ClassWiseSections getClassWiseSection(Long id);

    void updateClassWiseSection(Long id, String className, String sectionName);

    void updateClass(Long id, Classes classes);

    void deleteClassById(Long id);

    void updateSection(Long id, Sections sections);

    void deleteSectionById(Long id);
    List<Sections> getSectionsByClassId(Long classId);

    ClassWiseSections getClassWiseSectionByClassAndSection(Long classId, Long sectionId);
}
