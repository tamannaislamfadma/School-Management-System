package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.AdmissionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdmissionApplicationRepository extends JpaRepository<AdmissionApplication, Long> {
    // Find all applications that are waiting for approval
    List<AdmissionApplication> findByStatus(String status);
}