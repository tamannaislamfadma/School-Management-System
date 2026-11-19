package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.StaffEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffEntryRepository extends JpaRepository<StaffEntry, Long> {

    Optional<StaffEntry> findById(Long id);
}
