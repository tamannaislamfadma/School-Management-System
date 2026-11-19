package SCM.School.Management.System.controller;


import SCM.School.Management.System.model.*;
import SCM.School.Management.System.service.ClassWiseCourseService;
import SCM.School.Management.System.service.ClassWiseSectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class ClassWiseCourseController {


    private final ClassWiseSectionService classWiseSectionService;
    private final ClassWiseCourseService classWiseCourseService;

    public ClassWiseCourseController(ClassWiseSectionService clasSectionService, ClassWiseCourseService classWiseCourseService) {
        this.classWiseSectionService = clasSectionService;
        this.classWiseCourseService = classWiseCourseService;
    }

    //Wing and Division Part Controller Start
    @GetMapping ("/course")
    public String showForm(Model model) {


        List<Classes> classList = classWiseSectionService.getAllClasses();
        List<Courses> coursesList = classWiseCourseService.getAllCourses();
        List<ClassWiseCourse> classWiseCourseList = classWiseCourseService.getAllClassWiseCourses();

        //6-03-2025
        List<String> specificClassName = classWiseSectionService.getAllClasses().stream().map(Classes::getClassName).distinct().toList();


        model.addAttribute("classesName", specificClassName);
        //6-03-2025

        // Add list of classList to the model
        model.addAttribute("classList", classList);

// Bind form input to a new Class object
        model.addAttribute("classes", new Classes());

        model.addAttribute("coursesList",coursesList);

        model.addAttribute("courses", new Courses());

// Add list of class-section relationships to the model
        model.addAttribute("classWiseCourseList", classWiseCourseList);


// Bind form input to a new ClassWiseSection object
        model.addAttribute("classWiseCourse", new ClassWiseCourse());


        return "student/class-course";
    }




    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute Courses courses) {
        // Save the new course (Log it for debugging)
        System.out.println("Saving Sections: " + courses);
        classWiseCourseService.saveCourse(courses);
        return "redirect:/student/course";  // Redirect to manage page to show updated list
    }

    // Other existing methods...

    @PostMapping("/maClassToCourse")
    public String mapClassWiseCourse(@RequestParam Long classId, @RequestParam Long courseId) {

        System.out.println("Course ID: " + courseId);
        System.out.println("Class ID: " + classId);
        classWiseCourseService.saveClassWiseCourse(classId, courseId);
        return "redirect:/student/course";
    }

    //6-03-2025
    //Find courses by className

    @GetMapping("/findByClassName/classes/{className}")
    public ResponseEntity<List<ClassWiseCourse>> getClassWiseCourseByClassName(@PathVariable String className) {
        // Calling the service to find classWiseCourse by the class name
        return ResponseEntity.ok(classWiseCourseService.getAllClassWiseCourseByClassName(className));
    }

    //Delete classWiseCourse by id
    //6-03-2025
    @GetMapping("/deleteClassCourseById/{id}")
    public String deleteClassCourseById(@PathVariable Long id) {
        classWiseCourseService.deleteClassCourseById(id);
        return "redirect:/student/course";
    }


    //Update classSection by id
    //6-03-2025
    @PostMapping("/updateClassWiseCourse/{id}")
    public String updateClasCourseById(@PathVariable Long id, @RequestParam String className,
                                         @RequestParam String courseName) {
        classWiseCourseService.updateClassWiseCourse(id,className,courseName);
        return "redirect:/student/course";
    }



    //6-03-2025


    //Update and Delete Course
    @PostMapping("/updateCourseById/{id}")
    public String updateCourseById(@PathVariable Long id, @ModelAttribute Courses courses) {
        classWiseCourseService.updateCourse(id, courses);
        return "redirect:/student/course";
    }

    @GetMapping("/deleteCourseById/{id}")
    public String deleteCourseById(@PathVariable Long id) {
        classWiseCourseService.deleteCourseById(id);
        return "redirect:/student/course";
    }
    //22-02-25
//Class and Section Part Controller End

}
