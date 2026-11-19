package SCM.School.Management.System.serviceImpl;


import SCM.School.Management.System.model.ClassWiseCourse;
import SCM.School.Management.System.model.Classes;
import SCM.School.Management.System.model.Courses;
import SCM.School.Management.System.model.Sections;
import SCM.School.Management.System.repository.ClassWiseCourseRepository;
import SCM.School.Management.System.repository.ClassesRepository;
import SCM.School.Management.System.repository.CourseRepository;
import SCM.School.Management.System.service.ClassWiseCourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassWiseCourseServiceImpl implements ClassWiseCourseService {

    private final ClassWiseCourseRepository classWiseCourseRepository;
    private final ClassesRepository classesRepository;
    private final CourseRepository courseRepository;

    public ClassWiseCourseServiceImpl(ClassWiseCourseRepository classWiseCourseRepository, ClassesRepository classesRepository, CourseRepository courseRepository) {
        this.classWiseCourseRepository = classWiseCourseRepository;
        this.classesRepository = classesRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Courses> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void saveCourse(Courses courses) {
        courseRepository.save(courses);
    }

    @Override
    public void saveClassWiseCourse(Long classId, Long courseId) {

        Optional<Classes> classes = classesRepository.findById(classId);
        Optional<Courses> courses = courseRepository.findById(courseId);

        System.out.println("Is class present : "+classes.isPresent());
        System.out.println("Is course present :" + courses.isPresent());

        if (classes.isPresent() && courses.isPresent()){
            ClassWiseCourse classWiseCourse = new ClassWiseCourse();
            classWiseCourse.setClasses(classes.get());
            classWiseCourse.setCourses(courses.get());
            classWiseCourseRepository.save(classWiseCourse);

        }else {
            throw  new RuntimeException("Error saving the classWiseCourse");
        }

    }

    @Override
    public List<ClassWiseCourse> getAllClassWiseCourseByClassName(String className) {

        List<ClassWiseCourse> classWiseCourses = classWiseCourseRepository.findClassesWiseCoursesByClassName(className);

//        List<ClassWiseCourseDto> classWiseCourseDtos = classWiseCourses
//                .stream()
//                .map(classWiseCourse -> {
//                    ClassWiseCourseDto classWiseCourseDto = new ClassWiseCourseDto();
//                    classWiseCourseDto.setClassId(classWiseCourse.getClasses().getId());
//                    classWiseCourseDto.setClassName(classWiseCourse.getClasses().getClassName());
//                    classWiseCourseDto.setCourseId(classWiseCourse.getCourses().getId());
//                    classWiseCourseDto.setCourseName(classWiseCourse.getCourses().getCourseName());
//                    return classWiseCourseDto;
//                })
//                .toList();

        return classWiseCourses;
    }

    @Override
    public void deleteClassCourseById(Long id) {
        classWiseCourseRepository.deleteById(id);
    }

    @Override
    public ClassWiseCourse getClassWiseCourse(Long id) {
        return null;
    }

    @Override
    public void updateClassWiseCourse(Long id, String className, String courseName) {
        ClassWiseCourse classWiseCourse = classWiseCourseRepository.findById(id).get();

        Classes classes = classWiseCourse.getClasses();
        Courses courses = classWiseCourse.getCourses();

        if (!className.isEmpty() && !courseName.isEmpty()){
            classes.setClassName(className);
            courses.setCourseName(courseName);

            classWiseCourse.setClasses(classes);
            classWiseCourse.setCourses(courses);
        }
        classWiseCourseRepository.save(classWiseCourse);
    }

    @Override
    public void updateCourse(Long id, Courses courses) {
        Courses courseFromDb = courseRepository.findById(id).get();
        courseFromDb.setCourseName(courses.getCourseName());
        courseRepository.save(courses);
    }

    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<ClassWiseCourse> getAllClassWiseCourses() {
        return classWiseCourseRepository.findAll();
    }

    @Override
    public ClassWiseCourse getClassWiseSectionByClassAndSection(Long classId, Long courseId) {
        return classWiseCourseRepository.findByClassesIdAndCoursesId(classId,courseId).orElseThrow(()-> new
                RuntimeException("Class-Section combination not found."));
    }
}
