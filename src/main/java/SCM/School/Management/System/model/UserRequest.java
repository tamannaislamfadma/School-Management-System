package SCM.School.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity @Data
public class UserRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderName;
    private String role; // "TEACHER" or "STUDENT"
    private String requestType; // e.g., "Leave", "Correction", "General"
    private String message;
    private LocalDate date = LocalDate.now();

    private String status; // "PENDING", "REPLIED"
    private String adminReply;
}