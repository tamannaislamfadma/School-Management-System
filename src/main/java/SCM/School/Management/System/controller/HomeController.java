package SCM.School.Management.System.controller;

import SCM.School.Management.System.repository.StudentRepository;
import SCM.School.Management.System.repository.TeacherRepository;
import SCM.School.Management.System.service.TeacherAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherAssignmentService teacherAssignmentService;

    public HomeController(StudentRepository studentRepository,
                          TeacherRepository teacherRepository,
                          TeacherAssignmentService teacherAssignmentService) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.teacherAssignmentService = teacherAssignmentService;
    }

    // --- CONFLICT REMOVED: The "/" mapping is now in PublicController.java ---

    // EXISTING: Dashboard (Keep this as it is)
    @GetMapping("/dashboard/index")
    public String dashboardHome(Model model) {
        long totalStudents = studentRepository.count();
        long totalTeachers = teacherRepository.count();
        var assignmentList = teacherAssignmentService.getAllAssignments();

        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalTeachers", totalTeachers);
        model.addAttribute("assignments", assignmentList);

        return "dashboard/index";
    }
}