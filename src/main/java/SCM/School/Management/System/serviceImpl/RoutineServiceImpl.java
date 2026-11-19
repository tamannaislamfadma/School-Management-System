package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.ClassWiseSections;
import SCM.School.Management.System.model.Routine;
import SCM.School.Management.System.repository.RoutineRepository;
import SCM.School.Management.System.service.RoutineService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoutineServiceImpl implements RoutineService {
    @Autowired
    private RoutineRepository routineRepository;

    @Override
    public List<Routine> getAllRoutines() {
        return routineRepository.findAll();
    }

    @Override
    @Transactional
    public void addRoutine(Routine routine) {
        routineRepository.save(routine);
    }

    @Override
    @Transactional
    public void editRoutine(Routine routine, Long id) {
        Optional<Routine> existingRoutine = routineRepository.findById(id);
        if (existingRoutine.isPresent()) {
            Routine routineToUpdate = existingRoutine.get();

            routineToUpdate.setClassName(routine.getClassName());
            routineToUpdate.setSectionName(routine.getSectionName());

            // Updated Fields
            routineToUpdate.setCourseName(routine.getCourseName());
            routineToUpdate.setPeriod(routine.getPeriod());

            routineRepository.save(routineToUpdate);
        }
    }

    @Override
    public void deleteRoutine(Long id) {
        routineRepository.deleteById(id);
    }

    @Override
    public Routine getRoutineById(Long id) {
        return routineRepository.findById(id).orElse(null);
    }

    @Override
    public Routine getRoutineByClassNameAndSection(String className, String sectionName) {
        return routineRepository.findByClassNameAndSectionName(className, sectionName);
    }

    @Override
    public List<Routine> getRoutineByClassWiseSection(ClassWiseSections classWiseSection) {
        return routineRepository.findAll();
    }
}