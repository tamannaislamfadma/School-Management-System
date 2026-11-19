package SCM.School.Management.System.controller;

import SCM.School.Management.System.model.*;
import SCM.School.Management.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherPortalController {

    @Autowired private TeacherRepository teacherRepository;
    @Autowired private NoticeRepository noticeRepository;
    @Autowired private TeacherAssignmentRepository assignmentRepository;

    @GetMapping("/dashboard")
    public String teacherDashboard(Authentication authentication, Model model) {
        // 1. Get notices
        List<Notice> notices = noticeRepository.findByTargetAudience("TEACHER");
        if(notices.isEmpty()) notices = noticeRepository.findByTargetAudience("ALL");
        model.addAttribute("notices", notices);

        // 2. Try to find the teacher profile
        // In a real app, we find teacher by the username (authentication.getName())
        // For now, to prevent the 500 Crash, we just show an empty dashboard if no teacher is found
        List<Teacher> teachers = teacherRepository.findAllActiveTeachers();

        if (teachers.isEmpty()) {
            // Safe fallback to prevent crash
            model.addAttribute("teacher", new Teacher());
            model.addAttribute("myClasses", Collections.emptyList());
            return "teacher/dashboard";
        }

        Teacher currentTeacher = teachers.get(0); // Temporary: Get first teacher
        model.addAttribute("teacher", currentTeacher);

        // 3. Get Assignments safely
        List<TeacherAssignment> allAssignments = assignmentRepository.findAll();
        List<TeacherAssignment> myClasses = allAssignments.stream()
                .filter(a -> a.getTeacher() != null && a.getTeacher().getId().equals(currentTeacher.getId()))
                .toList();

        model.addAttribute("myClasses", myClasses);

        return "teacher/dashboard";
    }
}