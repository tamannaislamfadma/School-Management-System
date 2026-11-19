package SCM.School.Management.System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;  // Replaces Headline
    private String period;      // Replaces DocumentsUrl (Time/Period)

    private String className;
    private String sectionName;

    @ManyToOne
    @JoinColumn(name = "class_wise_section_id")
    private ClassWiseSections classWiseSections;
}