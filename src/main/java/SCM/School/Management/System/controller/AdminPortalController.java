package SCM.School.Management.System.controller;

import SCM.School.Management.System.model.*;
import SCM.School.Management.System.repository.*;
import SCM.School.Management.System.service.StorageService;
import SCM.School.Management.System.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPortalController {

    @Autowired private StudentRepository studentRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private AdmissionApplicationRepository admissionRepository;
    @Autowired private UserRequestRepository requestRepository;
    @Autowired private NoticeRepository noticeRepository;
    @Autowired private SchoolInfoRepository schoolInfoRepository;
    @Autowired private StorageService storageService;
    @Autowired private StudentService studentService;

    // ===========================
    // 1. DASHBOARD
    // ===========================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Cards
        model.addAttribute("totalStudents", studentRepository.count());
        model.addAttribute("totalTeachers", teacherRepository.count());
        model.addAttribute("pendingAdmissions", admissionRepository.findByStatus("PENDING"));

        // Notifications (Requests)
        model.addAttribute("teacherRequests", requestRepository.findByStatus("PENDING").stream().filter(r -> r.getRole().equals("TEACHER")).toList());
        model.addAttribute("studentRequests", requestRepository.findByStatus("PENDING").stream().filter(r -> r.getRole().equals("STUDENT")).toList());

        return "admin/dashboard";
    }

    // ===========================
    // 2. ACTIONS (Accept/Reject/Reply)
    // ===========================
    @GetMapping("/admission/approve/{id}")
    public String approveAdmission(@PathVariable Long id) {
        AdmissionApplication app = admissionRepository.findById(id).orElseThrow();

        Student student = new Student();
        student.setName(app.getStudentName());
        student.setFathersName(app.getFatherName());
        student.setMothersName(app.getMotherName());
        student.setGuardianContact(app.getMobileNumber());
        student.setEmail(app.getEmail());
        student.setAddress(app.getAddress());
        student.setPhotoUrl(app.getPhotoUrl());
        student.setActiveStatus(1);

        studentService.saveStudent(student);
        app.setStatus("APPROVED");
        admissionRepository.save(app);

        return "redirect:/admin/dashboard?success=Student Enrolled";
    }

    @GetMapping("/admission/reject/{id}")
    public String rejectAdmission(@PathVariable Long id) {
        AdmissionApplication app = admissionRepository.findById(id).orElseThrow();
        app.setStatus("REJECTED");
        admissionRepository.save(app);
        return "redirect:/admin/dashboard?info=Application Rejected";
    }

    @PostMapping("/request/reply")
    public String replyToRequest(@RequestParam Long requestId, @RequestParam String replyMessage) {
        UserRequest req = requestRepository.findById(requestId).orElseThrow();
        req.setAdminReply(replyMessage);
        req.setStatus("REPLIED");
        requestRepository.save(req);
        return "redirect:/admin/dashboard?success=Reply Sent";
    }

    // ===========================
    // 3. SETTINGS PAGES
    // ===========================

    // --- Notice Page ---
    @GetMapping("/notice/create")
    public String showNoticePage(Model model) {
        model.addAttribute("notice", new Notice());
        return "admin/settings/notice";
    }

    @PostMapping("/notice/save")
    public String saveNotice(@ModelAttribute Notice notice) {
        noticeRepository.save(notice);
        return "redirect:/admin/notice/create?success";
    }

    // --- Public Page Settings (General) ---
    @GetMapping("/settings/general")
    public String showGeneralSettings(Model model) {
        SchoolInfo info = getSchoolInfo();
        model.addAttribute("info", info);
        return "admin/settings/general";
    }

    // --- Public Page Settings (School Info) ---
    @GetMapping("/settings/school")
    public String showSchoolSettings(Model model) {
        SchoolInfo info = getSchoolInfo();
        model.addAttribute("info", info);
        return "admin/settings/school_info";
    }

    // --- Public Page Settings (Officers) ---
    @GetMapping("/settings/officers")
    public String showOfficersSettings(Model model) {
        SchoolInfo info = getSchoolInfo();
        model.addAttribute("info", info);
        return "admin/settings/officers";
    }

    @PostMapping("/settings/save")
    public String saveSettings(@ModelAttribute SchoolInfo info,
                               @RequestParam(required = false) MultipartFile circularFile,
                               @RequestParam(required = false) MultipartFile chairmanPic,
                               @RequestParam(required = false) MultipartFile principalPic) {

        // Handle File Uploads
        if(circularFile != null && !circularFile.isEmpty())
            info.setAdmissionCircularPath(storageService.saveFile(circularFile, "docs"));

        if(chairmanPic != null && !chairmanPic.isEmpty())
            info.setChairmanPhoto(storageService.saveFile(chairmanPic, "staff"));

        if(principalPic != null && !principalPic.isEmpty())
            info.setPrincipalPhoto(storageService.saveFile(principalPic, "staff"));

        // Use ID 1 always (Single Record)
        info.setId(1L);
        schoolInfoRepository.save(info);

        return "redirect:/admin/dashboard?success=Settings Updated";
    }

    // Helper to always get the single record (ID 1)
    private SchoolInfo getSchoolInfo() {
        return schoolInfoRepository.findById(1L).orElse(new SchoolInfo());
    }
}