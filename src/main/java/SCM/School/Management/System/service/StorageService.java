package SCM.School.Management.System.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class StorageService {
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public String saveFile(MultipartFile file, String subFolder) {
        if (file.isEmpty()) return null;
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + subFolder);
            if (!Files.exists(path)) Files.createDirectories(path);
            Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + subFolder + "/" + fileName; // Return web-accessible path
        } catch (IOException e) {
            throw new RuntimeException("Could not store file", e);
        }
    }
}