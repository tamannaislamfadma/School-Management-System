package SCM.School.Management.System.service;

import SCM.School.Management.System.model.SeatPlan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SeatPlanService {

    List<SeatPlan> getAllSeatPlans();
    void saveSeatPlan(SeatPlan seatPlan);
    void updateSeatPlan(Long id, SeatPlan seatPlan);
    void deleteSeatPlan(Long id);

    SeatPlan getSeatPlanById(Long id);

//    SeatPlan updateSeatPlan(Long id);
}
