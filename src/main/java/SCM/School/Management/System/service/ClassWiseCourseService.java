package SCM.School.Management.System.service;


import SCM.School.Management.System.model.ClassWiseCourse;
import SCM.School.Management.System.model.Courses;

import java.util.List;

public interface ClassWiseCourseService {
    List<Courses> getAllCourses();

    void saveCourse(Courses courses);

    void saveClassWiseCourse(Long classId, Long courseId);

    List<ClassWiseCourse> getAllClassWiseCourseByClassName(String className);

    void deleteClassCourseById(Long id);

    ClassWiseCourse getClassWiseCourse(Long id);

    void updateClassWiseCourse(Long id, String className, String courseName);

    void updateCourse(Long id, Courses courses);

    void deleteCourseById(Long id);

    List<ClassWiseCourse> getAllClassWiseCourses();

    ClassWiseCourse getClassWiseSectionByClassAndSection(Long classId, Long sectionId);
}
