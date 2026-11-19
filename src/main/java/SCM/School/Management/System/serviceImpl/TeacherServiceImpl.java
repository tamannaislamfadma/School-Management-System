package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.Teacher;
import SCM.School.Management.System.model.UserDtls;
import SCM.School.Management.System.repository.TeacherRepository;
import SCM.School.Management.System.repository.UserDtlsRepository;
import SCM.School.Management.System.service.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired private TeacherRepository teacherRepository;
    @Autowired private UserDtlsRepository userDtlsRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAllActiveTeachers();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveTeacher(Teacher teacher) {
        teacher.setActiveStatus(1);

        // ONLY create a login account if this is a NEW teacher
        if (teacher.getId() == null) {

            // Check if username/password were provided
            if (teacher.getManualUsername() != null && !teacher.getManualUsername().isEmpty() &&
                    teacher.getManualPassword() != null && !teacher.getManualPassword().isEmpty()) {

                // Create Login Account Manually
                UserDtls loginUser = new UserDtls();
                loginUser.setUserName(teacher.getManualUsername());
                // Encrypt the password you typed
                loginUser.setPassWord(passwordEncoder.encode(teacher.getManualPassword()));
                loginUser.setRole("TEACHER");

                userDtlsRepository.save(loginUser);
            } else {
                throw new RuntimeException("User ID and Password are required for new teachers!");
            }
        }

        // Save the Teacher Profile
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher != null){
            teacher.setActiveStatus(0);
            teacherRepository.save(teacher);
        }
    }

    @Override
    public void editTeacher(Teacher teacher, Long teacherId) {
        Teacher teacherFromDB = teacherRepository.findById(teacherId).orElse(null);

        if (teacherFromDB != null) {
            teacherFromDB.setName(teacher.getName());
            teacherFromDB.setFathersName(teacher.getFathersName());
            teacherFromDB.setMothersName(teacher.getMothersName());
            teacherFromDB.setEmail(teacher.getEmail());
            teacherFromDB.setAcademicBackground(teacher.getAcademicBackground());
            teacherFromDB.setAge(teacher.getAge());
            teacherFromDB.setNationality(teacher.getNationality());
            teacherFromDB.setPhoneNumber(teacher.getPhoneNumber());
            teacherFromDB.setReligion(teacher.getReligion());
            teacherFromDB.setDob(teacher.getDob());

            if(teacher.getActiveStatus() != null) {
                teacherFromDB.setActiveStatus(teacher.getActiveStatus());
            }

            teacherRepository.save(teacherFromDB);
        }
    }
}