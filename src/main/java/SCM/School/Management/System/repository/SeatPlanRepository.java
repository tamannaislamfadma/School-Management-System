package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.SeatPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatPlanRepository extends JpaRepository<SeatPlan, Long> {

    SeatPlan findByClassNameAndSectionName(String className, String sectionName);
    Optional<SeatPlan> findById(Long id);
}
