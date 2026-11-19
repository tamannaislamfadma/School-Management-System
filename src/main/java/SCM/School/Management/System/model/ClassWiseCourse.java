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
public class ClassWiseCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long classWiseCourseId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes classes;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses courses;

    @OneToMany(mappedBy = "classWiseCourse",cascade = CascadeType.ALL)
    private List<TeacherAssignment> teacherAssignments;
}
