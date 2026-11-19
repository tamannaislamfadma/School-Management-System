package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.UserDtls;
import SCM.School.Management.System.repository.UserDtlsRepository;
import SCM.School.Management.System.service.UserDtlsService;
import org.springframework.stereotype.Service;

@Service
public class UserDtlsServiceImpl implements UserDtlsService {

    private final UserDtlsRepository userDtlsRepository;

    public UserDtlsServiceImpl(UserDtlsRepository userDtlsRepository) {
        this.userDtlsRepository = userDtlsRepository;
    }

    @Override
    public UserDtls findByUsername(String username) {
        return userDtlsRepository.findByUserName(username);
    }

    @Override
    public void save(UserDtls user) {
        userDtlsRepository.save(user);
    }
}
