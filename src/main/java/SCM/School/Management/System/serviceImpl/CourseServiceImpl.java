package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.Courses;
import SCM.School.Management.System.repository.CourseRepository;
import SCM.School.Management.System.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Courses> getAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public List<Courses> getAllCoursesAssignedToTeacher(Long teacherId) {
        return courseRepository.findAllById(teacherId);
    }

    @Override
    public Courses getCourseById(Long courseId) {
        return courseRepository.findById(courseId).get();
    }
}
