package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.StaffEntry;
import SCM.School.Management.System.repository.StaffEntryRepository;
import SCM.School.Management.System.service.StaffEntryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffEntryServiceImpl implements StaffEntryService {

    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Override
    public List<StaffEntry> getAllStaffEntry() {
        return staffEntryRepository.findAll();
    }

    @Override
    @Transactional
    public void addStaffEntry(StaffEntry staffEntry) {
        staffEntryRepository.save(staffEntry);
    }

    @Override
    @Transactional
    public void deleteStaffEntry(Long id) {
        staffEntryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateStaffEntry(StaffEntry staffEntry, Long id) {
        Optional<StaffEntry> staffEntryOpt = staffEntryRepository.findById(id);

        if (staffEntryOpt.isPresent()) {
            StaffEntry existingStaffEntry = staffEntryOpt.get();

            existingStaffEntry.setNameEnglsih(staffEntry.getNameEnglsih());
            existingStaffEntry.setNameBangla(staffEntry.getNameBangla());
            existingStaffEntry.setPosition(staffEntry.getPosition());
            existingStaffEntry.setMobileNumber(staffEntry.getMobileNumber());
            existingStaffEntry.setStuffImage(staffEntry.getStuffImage());
            existingStaffEntry.setEmailAddress(staffEntry.getEmailAddress());

            staffEntryRepository.save(existingStaffEntry);
        }
    }

    @Override
    public StaffEntry getStaffEntryById(Long id) {
        return staffEntryRepository.findById(id).get();
    }
}
