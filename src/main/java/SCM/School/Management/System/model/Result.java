package SCM.School.Management.System.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class Result {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private ClassWiseCourse course; // Links Class+Subject

    private Double marks;
    private String examType; // "CT1", "HALF_YEARLY", "FINAL"
    private String grade; // A+, A, B...

    // Helper to calculate grade
    public void calculateGrade() {
        if(marks >= 80) this.grade = "A+";
        else if(marks >= 70) this.grade = "A";
        else if(marks >= 60) this.grade = "A-";
        else if(marks >= 50) this.grade = "B";
        else if(marks >= 40) this.grade = "C";
        else if(marks >= 33) this.grade = "D";
        else this.grade = "F";
    }
}