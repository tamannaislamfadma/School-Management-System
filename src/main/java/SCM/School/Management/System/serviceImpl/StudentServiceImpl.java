package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.Student;
import SCM.School.Management.System.model.UserDtls;
import SCM.School.Management.System.repository.StudentRepository;
import SCM.School.Management.System.repository.UserDtlsRepository;
import SCM.School.Management.System.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired private StudentRepository studentRepository;
    @Autowired private UserDtlsRepository userDtlsRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveStudent(Student student) {
        // 1. Generate Credentials ONLY if it's a new student
        if (student.getId() == null) {
            long count = studentRepository.count() + 1;
            String idSuffix = String.format("%04d", count); // 4 digits for students (0001)

            String cleanName = student.getName().trim().toLowerCase().replaceAll("\\s+", "");
            String username = cleanName + "." + idSuffix;
            String rawPassword = "pass" + idSuffix; // Example: pass0001

            UserDtls loginUser = new UserDtls();
            loginUser.setUserName(username);
            loginUser.setPassWord(passwordEncoder.encode(rawPassword));
            loginUser.setRole("STUDENT");

            userDtlsRepository.save(loginUser);

            System.out.println("=== STUDENT ACCOUNT CREATED ===");
            System.out.println("Username: " + username);
            System.out.println("Password: " + rawPassword);
        }

        studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    public Student getStudentByName(String studentName) {
        return studentRepository.findByName(studentName);
    }

    @Override
    @Transactional
    public void editStudent(Student student, Long id) {
        Student studentFromDB = studentRepository.findById(id).orElse(null);
        if (studentFromDB != null) {
            studentFromDB.setName(student.getName());
            studentFromDB.setFathersName(student.getFathersName());
            studentFromDB.setMothersName(student.getMothersName());
            studentFromDB.setEmail(student.getEmail());
            studentFromDB.setGuardianContact(student.getGuardianContact());
            studentFromDB.setAddress(student.getAddress());
            studentFromDB.setGender(student.getGender());
            studentFromDB.setDob(student.getDob());
            studentFromDB.setBloodGroup(student.getBloodGroup());
            studentFromDB.setActiveStatus(student.getActiveStatus());

            studentRepository.save(studentFromDB);
        }
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}