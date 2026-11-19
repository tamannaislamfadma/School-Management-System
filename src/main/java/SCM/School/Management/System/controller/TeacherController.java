package SCM.School.Management.System.controller;

import SCM.School.Management.System.model.Teacher;
import SCM.School.Management.System.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teacher/enrollment";
    }

    @PostMapping("/save")
    public String saveTeacher(@ModelAttribute Teacher teacher, RedirectAttributes redirectAttributes) {
        try {
            teacherService.saveTeacher(teacher);
            redirectAttributes.addFlashAttribute("successMessage", "Teacher Enrolled Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
            return "redirect:/teacher/create";
        }
        return "redirect:/teacher/list";
    }

    @GetMapping("/list")
    public String listTeachers(Model model){
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "teacher/list";
    }

    @GetMapping("/deleteTeacher/{teacherId}")
    public String afterDeletingTeacherList (@PathVariable Long teacherId){
        teacherService.deleteTeacher(teacherId);
        return "redirect:/teacher/list";
    }

    @GetMapping("/editTeacher/{teacherId}")
    public String showUpdateForm (Model model ,@PathVariable Long teacherId){
        Teacher teacher = teacherService.getTeacherById(teacherId);
        model.addAttribute("teacher",teacher);
        return "teacher/editTeacher";
    }

    @PostMapping("/updateTeacher/{teacherId}")
    public String editTeacher(@ModelAttribute Teacher teacher , @PathVariable Long teacherId){
        teacherService.editTeacher(teacher,teacherId);
        return "redirect:/teacher/list";
    }
}