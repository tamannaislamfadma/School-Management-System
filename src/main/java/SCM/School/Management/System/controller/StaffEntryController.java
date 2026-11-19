package SCM.School.Management.System.controller;

import SCM.School.Management.System.model.StaffEntry;
import SCM.School.Management.System.service.StaffEntryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/staff")
public class StaffEntryController {

    @Value("${file.upload.dir}")
    private String uploadDir;
    private final StaffEntryService staffEntryService;

    public StaffEntryController(@Value("${file.upload.dir}") String uploadDir, StaffEntryService staffEntryService) {
        this.uploadDir = uploadDir;
        this.staffEntryService = staffEntryService;
    }

    @GetMapping("/addStaff")
    public String showStaffEntryForm(Model model) {
        model.addAttribute("staffEntry", new StaffEntry());

        return "staff/addStaffEntry";
    }

    @GetMapping("/staffList")
    public String getAllStaffEntry(Model model) {
        List<StaffEntry> staffEntryList = staffEntryService.getAllStaffEntry();

        model.addAttribute("staffEntryList", staffEntryList);

        return "staff/staffEntryList";
    }

    @GetMapping("/editStaffEntry/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        StaffEntry staffEntry = staffEntryService.getStaffEntryById(id);
        model.addAttribute("staffEntry", staffEntry);

        return "staff/editStaffEntry";
    }

    @PostMapping("/updateStaffEntry/{id}")
    public String updateStaffEntry(
            @PathVariable Long id,
            @ModelAttribute StaffEntry staffEntry
    ) {
        staffEntryService.updateStaffEntry(staffEntry, id);

        return "redirect:/staff/staffList";
    }

    @GetMapping("/deleteStaffEntry/{id}")
    public String deleteStaffEntry(@PathVariable Long id) {
        staffEntryService.deleteStaffEntry(id);

        return "redirect:/staff/staffList";
    }


    @PostMapping("/saveStaffEntry")
    public String saveStaff(
            @ModelAttribute StaffEntry staffEntry,
            @RequestParam("photo") MultipartFile photo,
            Model model) {

        try {
            Files.createDirectories(Paths.get(uploadDir));
            System.out.println("Upload directory: " + uploadDir);

            if (!photo.isEmpty()) {
                // Use nameEnglsih to build filename safely
                String photoName = staffEntry.getNameEnglsih().replaceAll(" ", "_") + "_photo_" + UUID.randomUUID() + "." + getFileExtension(photo.getOriginalFilename());
                Path photoPath = Paths.get(uploadDir, photoName);
                System.out.println("Saving photo to: " + photoPath);
                Files.write(photoPath, photo.getBytes());

                // Set image path to the model
                staffEntry.setStuffImage("/uploads/staff/" + photoName);
            }

            staffEntryService.addStaffEntry(staffEntry);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "File upload failed!");
            return "staff/addStaffEntry";
        }

        return "redirect:/staff/staffList";
    }


    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


}
