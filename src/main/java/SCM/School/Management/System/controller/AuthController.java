package SCM.School.Management.System.controller;

import SCM.School.Management.System.model.UserDtls;
import SCM.School.Management.System.service.UserDtlsService;
import SCM.School.Management.System.serviceImpl.CustomUserDetailsServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthController {

    private final UserDtlsService userDtlsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomUserDetailsServiceImpl customUserDetailsService;

    public AuthController(UserDtlsService userDtlsService, BCryptPasswordEncoder passwordEncoder, CustomUserDetailsServiceImpl customUserDetailsService) {
        this.userDtlsService = userDtlsService;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new UserDtls());
        return "Authentication/signup";
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute UserDtls user) {
        // 1. Force Role to ADMIN
        user.setRole("ADMIN");

        // 2. Encode the password
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));

        // 3. Save the user
        userDtlsService.save(user);

        return "redirect:/login?signupSuccess";
    }

    @GetMapping("/login")
    public String showSignInPage() {
        return "Authentication/login";
    }
}