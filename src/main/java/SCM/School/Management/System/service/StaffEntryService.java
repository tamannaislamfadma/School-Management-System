package SCM.School.Management.System.service;

import SCM.School.Management.System.model.StaffEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StaffEntryService {

    List<StaffEntry> getAllStaffEntry();
    void addStaffEntry(StaffEntry staffEntry);
    void deleteStaffEntry(Long id);
    void updateStaffEntry(StaffEntry staffEntry, Long id);


    StaffEntry getStaffEntryById(Long id);
}
