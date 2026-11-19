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

public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String className;

    private String classCode;

    @OneToMany(mappedBy = "classes",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Sections> sections;

//    @OneToMany(mappedBy = "classes",cascade = CascadeType.PERSIST,orphanRemoval = true)
//    private List<StudentCourseEnrollment> studentCourseEnrollments;

    @OneToMany(mappedBy = "classes",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<ClassWiseSections> classWiseSections ;

    @OneToMany(mappedBy = "classes",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<ClassWiseCourse> classWiseCourses;
}