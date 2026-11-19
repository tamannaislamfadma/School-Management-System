package SCM.School.Management.System.service;

import SCM.School.Management.System.model.UserDtls;

public interface UserDtlsService {
    UserDtls findByUsername(String username);

    void save(UserDtls user);
}
