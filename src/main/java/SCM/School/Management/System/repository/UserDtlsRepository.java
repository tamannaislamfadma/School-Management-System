package SCM.School.Management.System.repository;

import SCM.School.Management.System.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDtlsRepository extends JpaRepository<UserDtls,Long> {
    UserDtls findByUserName(String username);
}
