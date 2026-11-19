package SCM.School.Management.System.service;

import SCM.School.Management.System.model.TeacherAssignment;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherAssignmentService {
    List<TeacherAssignment> getAllAssignments();
    @Transactional
    void assignTeacherToClass(TeacherAssignment assignment);


    void updateTeacherAssignment(Long id, TeacherAssignment teacherAssignment,Long teacherId, Long classId,Long sectionId,Long courseId);

    void deleteAssignment(Long id);

    TeacherAssignment getAssignmentById(Long id);
}