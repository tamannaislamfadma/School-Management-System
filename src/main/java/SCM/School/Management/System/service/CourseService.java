package SCM.School.Management.System.service;

import SCM.School.Management.System.model.Courses;

import java.util.List;

public interface CourseService {
    List<Courses> getAllCourse();
    List<Courses> getAllCoursesAssignedToTeacher(Long teacherId);

    Courses getCourseById(Long courseId);
}
