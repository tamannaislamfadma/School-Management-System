package SCM.School.Management.System.service;

import SCM.School.Management.System.model.Teacher;
import java.util.List;

public interface TeacherService {
    List<Teacher> getAllTeachers();
    Teacher getTeacherById(Long id);

    // Changed back to void (No need to return generated password)
    void saveTeacher(Teacher teacher);

    void deleteTeacher(Long id);
    void editTeacher(Teacher teacher, Long teacherId);
}