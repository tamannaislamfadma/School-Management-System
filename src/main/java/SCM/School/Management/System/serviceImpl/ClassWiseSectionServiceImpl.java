package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.ClassWiseSections;
import SCM.School.Management.System.model.Classes;
import SCM.School.Management.System.model.Sections;
import SCM.School.Management.System.repository.ClassWiseSectionRepository;
import SCM.School.Management.System.repository.ClassesRepository;
import SCM.School.Management.System.repository.SectionsRepository;
import SCM.School.Management.System.service.ClassWiseSectionService;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ClassWiseSectionServiceImpl implements ClassWiseSectionService {

    private final ClassWiseSectionRepository classWiseSectionRepository;
    private final ClassesRepository classesRepository;
    private final SectionsRepository sectionsRepository;


    public ClassWiseSectionServiceImpl(ClassWiseSectionRepository classWiseSectionRepository, ClassesRepository classesRepository, SectionsRepository sectionsRepository) {
        this.classWiseSectionRepository = classWiseSectionRepository;
        this.classesRepository = classesRepository;
        this.sectionsRepository = sectionsRepository;

    }

    @Override
    public List<Classes> getAllClasses() {
        return classesRepository.findAll();
    }

    @Override
    public List<Sections> getAllSections() {
        return sectionsRepository.findAll();
    }

    @Override
    public List<ClassWiseSections> getAllClassWiseSections() {
        return classWiseSectionRepository.findAll();
    }

    @Override
    public void saveClass(Classes classes) {

        if (classes != null){
            classesRepository.save(classes);
        }

    }

    @Override
    public void saveSections(Sections sections) {
        if (sections != null){
            sectionsRepository.save(sections);
        }
    }

    @Override
    public void saveClassWiseSections(Long classId, Long sectionId) {
        Optional<Classes> classes = classesRepository.findById(classId);
        Optional<Sections> sections = sectionsRepository.findById(sectionId);

        if (classes.isPresent() && sections.isPresent()){
            ClassWiseSections classWiseSections = new ClassWiseSections();
            classWiseSections.setClasses(classes.get());
            classWiseSections.setSections(sections.get());
            classWiseSectionRepository.save(classWiseSections);
        }else {
            throw new IllegalArgumentException("Invalid Class or Section ID");
        }

    }

    @Override
    public List<ClassWiseSections> getAllClassWiseSectionByClassName(String className) {



       List<ClassWiseSections>  classWiseSections = classWiseSectionRepository.findClassesWiseSectionsByClassName(className);

        return classWiseSections ;
//                .stream()
//                .map(classWiseSection ->{
//                    ClassWiseSectionDTO classWiseSectionDTO = new ClassWiseSectionDTO();
//                    classWiseSectionDTO.setClassId(classWiseSection.getClasses().getId());
//                    classWiseSectionDTO.setClassName(classWiseSection.getClasses().getClassName());
//                    classWiseSectionDTO.setSectionId(classWiseSection.getSections().getSectionId());
//                    classWiseSectionDTO.setSectionName(classWiseSection.getSections().getSectionName());
//
//                    return classWiseSectionDTO;
//                })
//                .toList();
    }

    @Override
    public void deleteClassSectionById(Long id) {
        ClassWiseSections classWiseSections = classWiseSectionRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Class Id"));
        classWiseSectionRepository.delete(classWiseSections);
    }

    @Override
    public ClassWiseSections getClassWiseSection(Long id) {
        return classWiseSectionRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Class Id"));
    }

    @Override
    public void updateClassWiseSection(Long id, String className, String sectionName) {

        ClassWiseSections optionalClassWiseSection = classWiseSectionRepository.findById(id).get();
        Classes classes = optionalClassWiseSection.getClasses();
        Sections sections = optionalClassWiseSection.getSections();

        if (!className.isEmpty() && !sectionName.isEmpty()){
            classes.setClassName(className);
            sections.setSectionName(sectionName);

            optionalClassWiseSection.setClasses(classes);
            optionalClassWiseSection.setSections(sections);
        }

        classWiseSectionRepository.save(optionalClassWiseSection);
    }

    @Override
    public void updateClass(Long id, Classes classes) {
        Classes classFromDB = classesRepository.findById(id).get();
        classFromDB.setClassName(classes.getClassName());
        classesRepository.save(classFromDB);
    }

    @Override
    public void deleteClassById(Long id) {
        classesRepository.deleteById(id);
    }

    @Override
    public void updateSection(Long id, Sections sections) {
        Sections sectionFromDb = sectionsRepository.findById(id).get();
        sectionFromDb.setSectionName(sections.getSectionName());
        sectionsRepository.save(sectionFromDb);
    }

    @Override
    public void deleteSectionById(Long id) {
        sectionsRepository.deleteById(id);
    }

    @Override
    public List<Sections> getSectionsByClassId(Long classId) {

        List<Sections> sectionsList = classWiseSectionRepository.findByClassesId(classId)
                .stream()
                .map(ClassWiseSections::getSections)
                .toList();

        return sectionsList;
    }

    public ClassWiseSections getClassWiseSectionByClassAndSection(Long classId, Long sectionId) {
        return classWiseSectionRepository.findByClassesIdAndSectionsSectionId(classId, sectionId)
                .orElseThrow(() -> new RuntimeException("Class-Section combination not found."));
    }
}
