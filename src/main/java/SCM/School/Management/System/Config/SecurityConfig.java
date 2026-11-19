package SCM.School.Management.System.Config;

import SCM.School.Management.System.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for development simplicity
                .authorizeHttpRequests(auth -> auth
                        // 1. PUBLIC PAGES (No Login Required)
                        .requestMatchers("/", "/home", "/about", "/contact", "/admission/**", "/public/**").permitAll()
                        // 2. STATIC ASSETS (CSS, JS, Images)
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/uploads/**", "/vendors/**", "/build/**").permitAll()
                        // 3. AUTHENTICATION (Login/Signup)
                        .requestMatchers("/login", "/signup", "/register").permitAll()
                        // 4. ROLE-BASED ACCESS
                                // Admin has full access to everything
                                .requestMatchers("/admin/**").hasRole("ADMIN")

// Admin can ALSO manage teachers/students, but Teachers/Students can only see their specific portals
                                .requestMatchers("/teacher/**").hasAnyRole("TEACHER", "ADMIN")
                                .requestMatchers("/student/**").hasAnyRole("STUDENT", "ADMIN")
                        // 5. EVERYTHING ELSE REQUIRES LOGIN
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login") // The URL the form submits to
                        .successHandler(customSuccessHandler()) // Logic to redirect based on Role
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().iterator().next().getAuthority();

            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin/dashboard");
            } else if (role.equals("ROLE_TEACHER")) {
                response.sendRedirect("/teacher/dashboard");
            } else if (role.equals("ROLE_STUDENT")) {
                response.sendRedirect("/student/dashboard");
            } else {
                response.sendRedirect("/");
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService::loadUserByUsername;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(List.of(authProvider));
    }
}



