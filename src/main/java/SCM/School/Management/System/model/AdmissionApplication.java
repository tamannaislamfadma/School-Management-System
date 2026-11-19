package SCM.School.Management.System.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class AdmissionApplication {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private String fatherName;
    private String motherName;
    private String mobileNumber;
    private String email;
    private String address;
    private String applyingClass;
    private String photoUrl;
    private String status; // "PENDING", "APPROVED", "REJECTED"
}