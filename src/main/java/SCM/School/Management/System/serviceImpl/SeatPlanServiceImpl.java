package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.Routine;
import SCM.School.Management.System.model.SeatPlan;
import SCM.School.Management.System.repository.SeatPlanRepository;
import SCM.School.Management.System.service.SeatPlanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatPlanServiceImpl implements SeatPlanService {

    @Autowired
    private SeatPlanRepository seatPlanRepository;

    @Override
    @Transactional
    public void saveSeatPlan(SeatPlan seatPlan) {
        seatPlanRepository.save(seatPlan);
    }

    @Override
    public List<SeatPlan> getAllSeatPlans() {
        return seatPlanRepository.findAll();
    }

    @Override
    public SeatPlan getSeatPlanById(Long id) {
        return seatPlanRepository.findById(id).get();
    }


    @Override
    @Transactional
    public void updateSeatPlan(Long id, SeatPlan seatPlan) {
        Optional<SeatPlan> existingSeatPlanOpt = seatPlanRepository.findById(id);

        if (existingSeatPlanOpt.isPresent()) {
            SeatPlan existingSeatPlan = existingSeatPlanOpt.get();

            existingSeatPlan.setRoll(seatPlan.getRoll());
            existingSeatPlan.setYear(seatPlan.getYear());
            existingSeatPlan.setExamName(seatPlan.getExamName());
            existingSeatPlan.setClassName(seatPlan.getClassName());
            existingSeatPlan.setSectionName(seatPlan.getSectionName());
            existingSeatPlan.setClassWiseSections(seatPlan.getClassWiseSections());

            seatPlanRepository.save(existingSeatPlan);
        }
    }

    @Override
    @Transactional
    public void deleteSeatPlan(Long id) {
        seatPlanRepository.deleteById(id);
    }

}
