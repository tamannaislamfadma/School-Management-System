package SCM.School.Management.System.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String fathersName;
    private String mothersName;
    private String email;
    private String phoneNumber;
    private Integer age;
    private String academicBackground;
    private String nationality;
    private String religion;
    private String dob;
    private Integer activeStatus;

    private String nidNumber;
    private String photoUrl;
    private String documentsUrl;
    private String documentType;

    // --- NEW MANUAL LOGIN FIELDS ---
    // @Transient means "Don't save this in the Teacher table"
    // We will use these to create the UserDtls entry instead.
    @Transient
    private String manualUsername;

    @Transient
    private String manualPassword;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<TeacherAssignment> teacherAssignments;
}