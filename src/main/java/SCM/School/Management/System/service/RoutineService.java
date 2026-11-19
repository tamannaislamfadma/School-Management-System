package SCM.School.Management.System.service;

import SCM.School.Management.System.model.ClassWiseSections;
import SCM.School.Management.System.model.Routine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoutineService {

    List<Routine> getAllRoutines();
    void addRoutine(Routine routine);
    void deleteRoutine(Long id);
    void editRoutine(Routine routine, Long id);


    Routine getRoutineById(Long id);


    Routine getRoutineByClassNameAndSection(String className, String sectionName);
    List<Routine> getRoutineByClassWiseSection(ClassWiseSections classWiseSection);

}
