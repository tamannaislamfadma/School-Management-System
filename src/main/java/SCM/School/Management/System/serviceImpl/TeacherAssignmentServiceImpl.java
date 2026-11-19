package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.ClassWiseCourse;
import SCM.School.Management.System.model.ClassWiseSections;
import SCM.School.Management.System.model.Teacher;
import SCM.School.Management.System.model.TeacherAssignment;
import SCM.School.Management.System.repository.SectionsRepository;
import SCM.School.Management.System.repository.TeacherAssignmentRepository;
import SCM.School.Management.System.service.ClassWiseCourseService;
import SCM.School.Management.System.service.ClassWiseSectionService;
import SCM.School.Management.System.service.TeacherAssignmentService;
import SCM.School.Management.System.service.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherAssignmentServiceImpl implements TeacherAssignmentService {

    private final TeacherAssignmentRepository repository;
    private final TeacherService teacherService;
    private final ClassWiseSectionService classWiseSectionService;
    private final ClassWiseCourseService classWiseCourseService;

    public TeacherAssignmentServiceImpl(TeacherAssignmentRepository repository, SectionsRepository sectionsRepository, TeacherService teacherService, ClassWiseSectionService classWiseSectionService, ClassWiseCourseService classWiseCourseService) {
        this.repository = repository;
        this.teacherService = teacherService;
        this.classWiseSectionService = classWiseSectionService;
        this.classWiseCourseService = classWiseCourseService;
    }

    @Override
    public List<TeacherAssignment> getAllAssignments() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void assignTeacherToClass(TeacherAssignment assignment) {

        repository.save(assignment);
    }

    @Override
    public void updateTeacherAssignment(Long id, TeacherAssignment teacherAssignment,Long teacherId, Long classId,Long sectionId,Long courseId) {

        TeacherAssignment teacherAssignmentFromDb = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TeacherAssignment not found with id: " + id));


        Teacher teacher = teacherService.getTeacherById(teacherId);
        ClassWiseSections classWiseSection = classWiseSectionService.getClassWiseSectionByClassAndSection(classId, sectionId);
        ClassWiseCourse classWiseCourse = classWiseCourseService.getClassWiseSectionByClassAndSection(classId, courseId);

        teacherAssignmentFromDb.setTeacher(teacher);
        teacherAssignmentFromDb.setClassWiseSections(classWiseSection);
        teacherAssignmentFromDb.setClassWiseCourse(classWiseCourse);

        repository.save(teacherAssignmentFromDb);
    }

    @Override
    public void deleteAssignment(Long id) {
        repository.deleteById(id);
    }

    @Override
    public TeacherAssignment getAssignmentById(Long id) {
        return repository.findById(id).get();
    }


}