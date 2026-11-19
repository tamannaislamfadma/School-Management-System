package SCM.School.Management.System.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String fathersName;
    private String mothersName;
    private String guardianContact;
    private String gender;
    private String dob;
    private String bloodGroup;
    private String address;
    private Integer activeStatus;
    private String enrolledBy;
    private String deactivatedBy;

//    private String classCode;
//    private String sectionCode;

    private String photoUrl;  // Stores file path
    private String documentsUrl;  // Stores file path

//    @OneToMany(mappedBy = "student",cascade = CascadeType.PERSIST)
//    private List<StudentCourseEnrollment> studentCourseEnrollmentList;
    @ManyToOne
    @JoinColumn(name = "class_wise_section_id")
    private ClassWiseSections classWiseSections;
}
