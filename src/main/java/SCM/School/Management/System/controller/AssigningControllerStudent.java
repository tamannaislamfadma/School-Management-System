//package SCM.School.Management.System.controller;
//
//import SCM.School.Management.System.model.Student;
//import SCM.School.Management.System.model.Subject;
//import SCM.School.Management.System.model.StudentCourseEnrollment;
//import SCM.School.Management.System.service.StudentService;
//import SCM.School.Management.System.service.StudentCourseEnrollmentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/student")
//public class AssigningControllerStudent {
//
//    private final StudentCourseEnrollmentService enrollmentService;
//    private final StudentService studentService;
////    private final SubjectRepository subjectRepository;
//
//    @Autowired
//    public AssigningControllerStudent(StudentCourseEnrollmentService enrollmentService,
//                                      StudentService studentService){
//        this.enrollmentService = enrollmentService;
//        this.studentService = studentService;
//    }
//
//    /**
//     * Show the student subject assignment form.
//     */
//    @GetMapping("/assignSubject")
//    public String showAssignmentForm(Model model) {
//        model.addAttribute("students", studentService.getAllStudents());
//
//        return "student/assignSubject";
//    }
//
//    /**
//     * Search for a student by ID or Name.
//     */
//    @GetMapping("/searchStudent")
//    public String searchStudent(@RequestParam(required = false) Long studentId,
//                                @RequestParam(required = false) String studentName,
//                                Model model) {
//        Optional<Student> student = Optional.empty();
//
//        if (studentId != null) {
//            student = studentService.getStudentById(studentId);
//        } else if (studentName != null && !studentName.isEmpty()) {
//            student = Optional.ofNullable(studentService.getStudentByName(studentName));
//        }
//
//        if (student.isPresent()) {
//            model.addAttribute("student", student.get());
////            model.addAttribute("subjects", subjectRepository.findAll());
//            model.addAttribute("assignments", enrollmentService.getSubjectsByStudentId(student.get().getId())); // Fetch assigned subjects
//        } else {
//            model.addAttribute("error", "Student not found!");
//        }
//
//        return "student/assignSubject";
//    }
//
//    /**
//     * Assign a subject to a student.
//     */
//    @PostMapping("/assignSubject")
//    public String enrollStudent(@RequestParam Long studentId,
//                                @RequestParam(required = false) Long subjectId,
//                                Model model) {
//        if (subjectId == null) {
//            model.addAttribute("error", "Please select a subject!");
//            return "redirect:/student/assignSubject?error";
//        }
//
//        Optional<Student> studentOpt = studentService.getStudentById(studentId);
////        Optional<Subject> subjectOpt = subjectRepository.findById(subjectId);
//
//        if (studentOpt.isEmpty() || subjectOpt.isEmpty()) {
//            model.addAttribute("error", "Invalid Student or Subject!");
//            return "redirect:/student/assignSubject?error";
//        }
//
//        StudentCourseEnrollment enrollment = new StudentCourseEnrollment();
//        enrollment.setStudent(studentOpt.get());
//        enrollment.setSubject(subjectOpt.get());
//
//        enrollmentService.assignStudentToSubject(enrollment);
//        return "redirect:/student/assignSubject?success";
//    }
//
//}
