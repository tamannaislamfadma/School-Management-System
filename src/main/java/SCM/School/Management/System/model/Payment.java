package SCM.School.Management.System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String month;

    // Link to Student Entity so we can select from a dropdown
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Fees (using Double for calculations)
    private Double admissionFee = 0.0;
    private Double tuitionFee = 0.0;
    private Double labFee = 0.0;
    private Double extraCurricularFee = 0.0;
    private Double developmentFee = 0.0;
    private Double libraryFee = 0.0;
    private Double examFee = 0.0;
    private Double insuranceFee = 0.0;
    private Double others = 0.0;

    private Double totalAmount = 0.0;
}