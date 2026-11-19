package SCM.School.Management.System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roll;
    private Long year;
    private String examName;
    private String className;
    private String sectionName;

    @ManyToOne
    @JoinColumn(name = "class_wise_section_id", nullable = false)
    private ClassWiseSections classWiseSections;

}
