package SCM.School.Management.System.serviceImpl;
import SCM.School.Management.System.model.StudentCourseEnrollment;
import SCM.School.Management.System.model.Subject;
import SCM.School.Management.System.repository.StudentCourseEnrollmentRepository;
import SCM.School.Management.System.service.StudentCourseEnrollmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseEnrollmentServiceImpl implements StudentCourseEnrollmentService {
    private final StudentCourseEnrollmentRepository studentCourseEnrollmentRepository;

    public StudentCourseEnrollmentServiceImpl(StudentCourseEnrollmentRepository studentCourseEnrollmentRepository) {
        this.studentCourseEnrollmentRepository = studentCourseEnrollmentRepository;
    }

    @Override
    public void assignStudentToSubject(StudentCourseEnrollment studentCourseEnrollment) {
       studentCourseEnrollmentRepository.save(studentCourseEnrollment) ;
    }

    @Override
    public List<StudentCourseEnrollment> getAllAssignments() {
        return studentCourseEnrollmentRepository.findAll();
    }


    @Override
    public List<Subject> getSubjectsByStudentId(Long studentId) {
        List<StudentCourseEnrollment> enrollments = studentCourseEnrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(StudentCourseEnrollment::getSubject)
                .toList();
    }
}
