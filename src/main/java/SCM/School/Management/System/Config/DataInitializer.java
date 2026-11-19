package SCM.School.Management.System.Config;

import SCM.School.Management.System.model.UserDtls;
import SCM.School.Management.System.repository.UserDtlsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserDtlsRepository userRepo,
                                       PasswordEncoder passwordEncoder) {

        return args -> {
            // Check if admin already exists
            UserDtls existingAdmin = userRepo.findByUserName("admin");

            if (existingAdmin == null) {
                UserDtls admin = new UserDtls();
                admin.setUserName("admin");
                admin.setPassWord(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");

                userRepo.save(admin);
                System.out.println("==== ADMIN USER CREATED ====");
                System.out.println("Username: admin");
                System.out.println("Password: admin123");
            } else {
                System.out.println("Admin user already exists. Skipping creation.");
            }
        };
    }
}
