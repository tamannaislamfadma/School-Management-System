package SCM.School.Management.System.controller;


import SCM.School.Management.System.model.*;
import SCM.School.Management.System.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/teacher")
public class AssigningControllerTeacher {

    private final TeacherService teacherService;
    private final ClassWiseSectionService classWiseSectionService;
    private final TeacherAssignmentService teacherAssignmentService;

    private final ClassWiseCourseService classWiseCourseService;

    public AssigningControllerTeacher(
            TeacherService teacherService,
            ClassWiseSectionService classWiseSectionService,
            TeacherAssignmentService teacherAssignmentService,
            ClassWiseCourseService classWiseCourseService) {
        this.teacherService = teacherService;
        this.classWiseSectionService = classWiseSectionService;
        this.teacherAssignmentService = teacherAssignmentService;

        this.classWiseCourseService = classWiseCourseService;
    }

    @GetMapping("/assign")
    public String showAssignmentForm(Model model) {
        List<Classes> classList = classWiseSectionService.getAllClasses();
//        List<ClassWiseSections> classWiseSectionList = classWiseSectionService.getAllClassWiseSections();
        List<Teacher> teacherList = teacherService.getAllTeachers();
//        List<Courses> coursesList = classWiseCourseService.getAllCourses();
//        List<String> specificClassName = classWiseSectionService.getAllClasses().stream().map(Classes::getClassName).distinct().toList();
        model.addAttribute("classList", classList);
//        model.addAttribute("className", specificClassName);
//        model.addAttribute("classWiseSectionList", classWiseSectionList);
        model.addAttribute("teacherList", teacherList);
//        model.addAttribute("coursesList", coursesList);
        model.addAttribute("assignment", new TeacherAssignment());
        return "teacher/assignment";
    }



    @GetMapping("/findSectionByClassName/{className}")
    public ResponseEntity<List<Map<String, Object>>>getClassWiseSectionByClassName(@PathVariable String className) {




        // Get the sections
        List<ClassWiseSections> sections = classWiseSectionService.getAllClassWiseSectionByClassName(className);

        List<Map<String,Object>> response= sections.stream()
                .map(section ->{
                    Map<String,Object> map = new HashMap<>();
                    map.put("sectionId", section.getSections().getSectionId());
                    map.put("sectionName", section.getSections().getSectionName());

                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);

    }

    @GetMapping("/findCourseByClassName/{className}")
    public ResponseEntity<List<Map<String, Object>>> getClassWiseCourseByClassName(@PathVariable String className) {

        // Get the sections
        List<ClassWiseCourse> courses = classWiseCourseService.getAllClassWiseCourseByClassName(className);

        List<Map<String,Object>> response= courses.stream()
                .map(course ->{
                    Map<String,Object> map = new HashMap<>();
                    map.put("courseId", course.getCourses().getId());
                    map.put("courseName", course.getCourses().getCourseName());

                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/assignment")
    public String assignTeacherToClass(
            @RequestParam Long teacherId,
            @RequestParam Long classId,
            @RequestParam Long sectionId,
            @RequestParam Long courseId,
            @ModelAttribute TeacherAssignment assignment) {

        Teacher teacher = teacherService.getTeacherById(teacherId);
        ClassWiseSections classWiseSection = classWiseSectionService.getClassWiseSectionByClassAndSection(classId, sectionId);
        ClassWiseCourse classWiseCourse = classWiseCourseService.getClassWiseSectionByClassAndSection(classId, courseId);

        assignment.setTeacher(teacher);
        assignment.setClassWiseSections(classWiseSection);
        assignment.setClassWiseCourse(classWiseCourse);

        teacherAssignmentService.assignTeacherToClass(assignment);

        return "redirect:/teacher/view";
    }


    @GetMapping("/view")
    public String listAssignments(Model model) {
        List<TeacherAssignment> assignments = teacherAssignmentService.getAllAssignments();

        List<Classes> classList = classWiseSectionService.getAllClasses();

        List<ClassWiseSections> classWiseSectionList = classWiseSectionService.getAllClassWiseSections();
        List<Teacher> teacherList = teacherService.getAllTeachers();
        List<Courses> coursesList = classWiseCourseService.getAllCourses();
        List<String> specificClassName = classWiseSectionService.getAllClasses().stream().map(Classes::getClassName).distinct().toList();

        model.addAttribute("assignments", assignments);
        model.addAttribute("assignment", new TeacherAssignment());
        model.addAttribute("classList", classList);
        model.addAttribute("className", specificClassName);
        model.addAttribute("classWiseSectionList", classWiseSectionList);
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("coursesList", coursesList);
        return "teacher/assignedTeacher";
    }

    @PostMapping("/editAssignment/{id}")
    public String editAssignmentByID(
            @PathVariable Long id,
            @RequestParam Long teacherId,
            @RequestParam Long classId,
            @RequestParam Long sectionId,
            @RequestParam Long courseId,
            @ModelAttribute TeacherAssignment teacherAssignment
       ) {

            teacherAssignmentService.updateTeacherAssignment(id,teacherAssignment,teacherId,classId,sectionId,courseId);

        return "redirect:/teacher/view";
    }
    @GetMapping("/deleteAssignment/{id}")
    public String deleteAssignmentByID(@PathVariable Long id) {
        teacherAssignmentService.deleteAssignment(id);
        return "redirect:/teacher/view";
    }



}
