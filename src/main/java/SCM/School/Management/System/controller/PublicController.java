package SCM.School.Management.System.controller;

import SCM.School.Management.System.model.AdmissionApplication;
import SCM.School.Management.System.model.Notice;
import SCM.School.Management.System.repository.AdmissionApplicationRepository;
import SCM.School.Management.System.repository.NoticeRepository;
import SCM.School.Management.System.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class PublicController {

    @Autowired private NoticeRepository noticeRepository;
    @Autowired private AdmissionApplicationRepository admissionRepository;
    @Autowired private StorageService storageService;

    // --- 1. HOME PAGE ---
    @GetMapping("/")
    public String publicHome(Model model) {
        // Fetch Notices for the Ticker (Scrolling)
        List<Notice> tickerNotices = noticeRepository.findByIsScrollableTrue();

        // Fetch Notices for the Main Body
        List<Notice> mainNotices = noticeRepository.findByTargetAudience("MAIN");

        model.addAttribute("tickers", tickerNotices);
        model.addAttribute("notices", mainNotices);

        // Static School Info
        model.addAttribute("schoolName", "CAPTAIN CAMPUS");
        model.addAttribute("schoolAddress", "72/4, Mirpur-14, Dhaka");
        model.addAttribute("schoolEmail", "info@captaincampus.edu.bd");

        return "public/home";
    }

    // --- 2. ADMISSION PAGE ---
    @GetMapping("/admission/apply")
    public String showAdmissionForm(Model model) {
        model.addAttribute("application", new AdmissionApplication());
        return "public/apply";
    }

    // --- 3. SUBMIT ADMISSION ---
    @PostMapping("/admission/submit")
    public String submitAdmission(@ModelAttribute AdmissionApplication application,
                                  @RequestParam("studentPhoto") MultipartFile photo) {

        // 1. Save Photo
        String photoUrl = storageService.saveFile(photo, "applicants");
        application.setPhotoUrl(photoUrl);

        // 2. Set Default Status
        application.setStatus("PENDING");

        // 3. Save to DB
        admissionRepository.save(application);

        return "redirect:/?success=Application Submitted Successfully";
    }
}