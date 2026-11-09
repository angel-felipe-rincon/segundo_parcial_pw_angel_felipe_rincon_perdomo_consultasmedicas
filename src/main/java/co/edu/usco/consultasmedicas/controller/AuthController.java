package co.edu.usco.consultasmedicas.controller;

import java.util.Set;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    private static final Set<String> ROLES = Set.of("ADMINISTRADOR", "MEDICO", "PACIENTE");

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/")
    public String inicio(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(ROLES::contains)
                .toList());
        return "inicio";
    }

    @RequestMapping("/403")
    public String error403() {
        return "403";
    }
}
