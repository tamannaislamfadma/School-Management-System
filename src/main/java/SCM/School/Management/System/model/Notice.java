package SCM.School.Management.System.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity @Data
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 5000)
    private String content;
    private LocalDate date = LocalDate.now();

    // Target: "MAIN", "TEACHER", "STUDENT", "ALL"
    private String targetAudience;
    private boolean isScrollable; // For the TV ticker
}