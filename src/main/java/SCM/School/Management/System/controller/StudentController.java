package SCM.School.Management.System.controller;

import SCM.School.Management.System.model.*;
import SCM.School.Management.System.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final ClassWiseSectionService classWiseSectionService;

    public StudentController(StudentService studentService, ClassWiseSectionService classWiseSectionService) {
        this.studentService = studentService;
        this.classWiseSectionService = classWiseSectionService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("classList", classWiseSectionService.getAllClasses());
        return "student/studentEnrollment";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student,
                              @RequestParam Long classId,
                              @RequestParam Long sectionId,
                              Model model) {
        try {
            student.setPhotoUrl("default.png");
            student.setDocumentsUrl("default.pdf");

            ClassWiseSections classWiseSection = classWiseSectionService.getClassWiseSectionByClassAndSection(classId, sectionId);
            student.setClassWiseSections(classWiseSection);
            student.setActiveStatus(1);

            studentService.saveStudent(student);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/student/create?error";
        }
        return "redirect:/student/list";
    }

    @GetMapping("/list")
    public String getAllStudents(Model model) {
        model.addAttribute("studentList", studentService.getAllStudents());
        return "student/list";
    }

    // --- Added Delete Method ---
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student/list";
    }

    // --- Added Edit Methods ---
    @GetMapping("/editStudent/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("classList", classWiseSectionService.getAllClasses());
        // Note: You need a student/editStudent.html template.
        // If you don't have one, copy studentEnrollment.html and rename it,
        // then change form action to /student/updateStudent/{id}
        return "student/editStudent";
    }

    @PostMapping("/updateStudent/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student) {
        studentService.editStudent(student, id);
        return "redirect:/student/list";
    }
}