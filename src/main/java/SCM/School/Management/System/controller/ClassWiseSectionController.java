package SCM.School.Management.System.controller;


import SCM.School.Management.System.model.ClassWiseSections;
import SCM.School.Management.System.model.Classes;
import SCM.School.Management.System.model.Sections;
import SCM.School.Management.System.service.ClassWiseSectionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.List;
@Controller
@RequestMapping("/student")
public class ClassWiseSectionController {

    
    private final ClassWiseSectionService classWiseSectionService;

    public ClassWiseSectionController(ClassWiseSectionService clasSectionService) {
        this.classWiseSectionService = clasSectionService;
    }

    //Wing and Division Part Controller Start
    @GetMapping ("/manage")
    public String showForm(Model model) {
        // Fetch updated list of wings, divisions, and wing-wise divisions
        List<Classes> classList = classWiseSectionService.getAllClasses();
        List<Sections> sectionList = classWiseSectionService.getAllSections();
        List<ClassWiseSections> classWiseSectionList = classWiseSectionService.getAllClassWiseSections(); // Corrected method name

        //22-02-2025
        List<String> specificClassName = classWiseSectionService.getAllClasses().stream().map(Classes::getClassName).distinct().toList();


        model.addAttribute("classesName", specificClassName);
        //22-02-2025

        // Add list of classList to the model
        model.addAttribute("classList", classList);

// Bind form input to a new Class object
        model.addAttribute("classes", new Classes());

// Add list of sectionList to the model
        model.addAttribute("sectionList", sectionList);

// Bind form input to a new Section object
        model.addAttribute("sections", new Sections());

// Add list of class-section relationships to the model
        model.addAttribute("classWiseSectionList", classWiseSectionList);

// Bind form input to a new ClassWiseSection object
        model.addAttribute("classWiseSection", new ClassWiseSections());


        return "student/class-section";
    }


    @PostMapping("/addClass")
    public String addClass(@ModelAttribute Classes classes) {
        // Save the new class (Log it for debugging)
        System.out.println("Saving class: " + classes);
        classWiseSectionService.saveClass(classes);
        return "redirect:/student/manage";  // Redirect to manage page to show updated list
    }

    @PostMapping("/addSection")
    public String addSection(@ModelAttribute Sections sections) {
        // Save the new section (Log it for debugging)
        System.out.println("Saving Sections: " + sections);
        classWiseSectionService.saveSections(sections);
        return "redirect:/student/manage";  // Redirect to manage page to show updated list
    }

    // Other existing methods...

    @PostMapping("/mapClassSections")
    public String mapClassWiseSection(@RequestParam Long classId, @RequestParam Long sectionId) {
        classWiseSectionService.saveClassWiseSections(classId, sectionId);
        return "redirect:/student/manage";
    }

    //22-02-25
    //Find sections by className

    @GetMapping("/findByClassName/{className}")
    public ResponseEntity<List<ClassWiseSections>> getClassWiseSectionByClassName(@PathVariable String className) {
        // Calling the service to find classWiseSection by the class name
        return ResponseEntity.ok(classWiseSectionService.getAllClassWiseSectionByClassName(className));
    }

    //Delete classWiseSection  by id
    @GetMapping("/deleteClassSectionById/{id}")
    public String deleteClassSectionById(@PathVariable Long id) {
        classWiseSectionService.deleteClassSectionById(id);
        return "redirect:/student/manage";
    }


    //Update classSection by id
    @PostMapping("/updateClassWiseSection/{id}")
    public String updateClassSectionById(@PathVariable Long id, @RequestParam String className,
                                         @RequestParam String sectionName) {
        classWiseSectionService.updateClassWiseSection(id,className, sectionName);
        return "redirect:/student/manage";
    }
    //22-02-25


    //22-02-25

    //Update and Delete class
    @PostMapping("/updateClassById/{id}")
    public String updateClassById(@PathVariable Long id, @ModelAttribute Classes classes) {
        classWiseSectionService.updateClass(id, classes);
        return "redirect:/student/manage";
    }

    @GetMapping("/deleteClassById/{id}")
    public String deleteClassById(@PathVariable Long id) {
        classWiseSectionService.deleteClassById(id);
        return "redirect:/student/manage";
    }

    //Update and Delete Section
    @PostMapping("/updateSectionById/{id}")
    public String updateSectionById(@PathVariable Long id, @ModelAttribute Sections sections) {
        classWiseSectionService.updateSection(id, sections);
        return "redirect:/student/manage";
    }

    @GetMapping("/deleteSectionById/{id}")
    public String deleteSectionById(@PathVariable Long id) {
        classWiseSectionService.deleteSectionById(id);
        return "redirect:/student/manage";
    }
    //22-02-25
//Class and Section Part Controller End

}
