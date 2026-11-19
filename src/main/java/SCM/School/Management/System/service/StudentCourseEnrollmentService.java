package SCM.School.Management.System.service;
import SCM.School.Management.System.model.StudentCourseEnrollment;

import SCM.School.Management.System.model.Subject;
import org.springframework.stereotype.Service;
import java.util.List;


public interface StudentCourseEnrollmentService {

    List<StudentCourseEnrollment> getAllAssignments();

    void assignStudentToSubject(StudentCourseEnrollment enrollment);
    List<Subject> getSubjectsByStudentId(Long studentId);

}
