package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.UserDtls;
import SCM.School.Management.System.service.CustomUserDetailsService;
import SCM.School.Management.System.service.UserDtlsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserDtlsService userDtlsService;

    public CustomUserDetailsServiceImpl(UserDtlsService userDtlsService) {
        this.userDtlsService = userDtlsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDtls user = userDtlsService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new User(
                user.getUserName(),
                user.getPassWord(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
