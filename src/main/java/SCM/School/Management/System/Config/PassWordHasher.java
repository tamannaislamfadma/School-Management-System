package SCM.School.Management.System.Config;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class PassWordHasher {
    public static void main(String[] args) {

        BCryptPasswordEncoder endoder = new BCryptPasswordEncoder();
        String rawPassword = "admin101";
        String hashPassword = endoder.encode(rawPassword);

        System.out.println(hashPassword);
    }
}
