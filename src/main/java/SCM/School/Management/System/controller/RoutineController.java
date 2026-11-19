package SCM.School.Management.System.controller;

import SCM.School.Management.System.model.ClassWiseSections;
import SCM.School.Management.System.model.Routine;
import SCM.School.Management.System.service.ClassWiseSectionService;
import SCM.School.Management.System.service.RoutineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class RoutineController {

    private final RoutineService routineService;
    private final ClassWiseSectionService classWiseSectionService;

    public RoutineController(RoutineService routineService, ClassWiseSectionService classWiseSectionService) {
        this.routineService = routineService;
        this.classWiseSectionService = classWiseSectionService;
    }

    @GetMapping("/addroutine")
    public String showAddRoutine(Model model) {
        model.addAttribute("routine", new Routine());
        model.addAttribute("classList", classWiseSectionService.getAllClasses());
        return "student/addRoutine";
    }

    @PostMapping("/saveroutine")
    public String saveRoutine(@ModelAttribute Routine routine,
                              @RequestParam("classId") Long classId,
                              @RequestParam("sectionId") Long sectionId) {
        try {
            ClassWiseSections cws = classWiseSectionService.getClassWiseSectionByClassAndSection(classId, sectionId);
            routine.setClassWiseSections(cws);
            routine.setClassName(cws.getClasses().getClassName());
            routine.setSectionName(cws.getSections().getSectionName());

            routineService.addRoutine(routine);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/student/routinelist";
    }

    @GetMapping("/routinelist")
    public String getAllRoutines(Model model) {
        model.addAttribute("routineList", routineService.getAllRoutines());
        return "student/routineList";
    }

    // --- Added Delete ---
    @GetMapping("/deleteroutine/{id}")
    public String deleteRoutine(@PathVariable Long id) {
        routineService.deleteRoutine(id);
        return "redirect:/student/routinelist";
    }

    // --- Added Edit ---
    @GetMapping("/updateroutine/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Routine routine = routineService.getRoutineById(id);
        model.addAttribute("routine", routine);
        model.addAttribute("classList", classWiseSectionService.getAllClasses());
        return "student/editRoutine"; // Ensure you create this HTML
    }

    @PostMapping("/updateroutine/{id}")
    public String updateRoutine(@PathVariable Long id, @ModelAttribute Routine routine) {
        routineService.editRoutine(routine, id);
        return "redirect:/student/routinelist";
    }
}