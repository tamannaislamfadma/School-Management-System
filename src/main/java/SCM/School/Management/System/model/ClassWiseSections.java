package SCM.School.Management.System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassWiseSections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classWiseSectionId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes classes;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Sections sections;


    @OneToMany(mappedBy = "classWiseSections",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TeacherAssignment> teacherAssignments;

    @OneToMany(mappedBy = "classWiseSections",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Student> students;


}
