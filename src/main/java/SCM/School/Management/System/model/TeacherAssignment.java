package SCM.School.Management.System.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "class_wise_section_id")
    private ClassWiseSections classWiseSections;

    @ManyToOne
    @JoinColumn(name = "class_wise_course_id")
    private ClassWiseCourse classWiseCourse;





}