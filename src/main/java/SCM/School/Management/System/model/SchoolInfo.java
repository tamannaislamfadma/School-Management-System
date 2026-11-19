package SCM.School.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class SchoolInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // General Info
    private String email;
    private String phone;
    private String address;
    private String officeHours;

    // School Info
    @Column(length = 5000)
    private String aboutUs;
    private String admissionCircularPath; // PDF File Path

    // Officers Info (Chairman)
    private String chairmanName;
    private String chairmanPhoto;
    @Column(length = 2000)
    private String chairmanMessage;

    // Officers Info (Principal)
    private String principalName;
    private String principalPhoto;
    @Column(length = 2000)
    private String principalMessage;
}