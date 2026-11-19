package SCM.School.Management.System.service;

import SCM.School.Management.System.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface StudentService {



    List<Student> getAllStudents();

   void saveStudent(Student student);

    Student getStudentById(Long studentId);
    Student getStudentByName(String studentName);


    void editStudent(Student student, Long id);

    void deleteStudent(Long id);
}
