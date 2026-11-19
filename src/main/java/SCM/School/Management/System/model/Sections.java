package SCM.School.Management.System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionId;

    private String sectionName;

    private  String sectionCode;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes classes;

//    @OneToMany(mappedBy = "sectionLookup",cascade = CascadeType.PERSIST)
//    private List<TeacherAssignment> teacherAssignments;

//    @OneToMany(mappedBy = "sections",cascade = CascadeType.PERSIST)
//    private List<StudentCourseEnrollment> studentCourseEnrollments;


    @OneToMany(mappedBy = "sections",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ClassWiseSections> classWiseSections;




}
