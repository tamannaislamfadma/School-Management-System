package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // Find notices that should scroll on the TV ticker
    List<Notice> findByIsScrollableTrue();

    // Find notices for a specific target (e.g., "MAIN", "TEACHER")
    List<Notice> findByTargetAudience(String targetAudience);
}