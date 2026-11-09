package co.edu.usco.consultasmedicas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfException;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
            .authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers("/login", "/403", "/error").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                .requestMatchers("/", "/consultas")
                    .hasAnyAuthority("ADMINISTRADOR", "MEDICO", "PACIENTE")
                .requestMatchers("/consultas/horario/**")
                    .hasAnyAuthority("ADMINISTRADOR", "PACIENTE")
                .requestMatchers("/consultas/nueva", "/consultas/guardar",
                        "/consultas/editar/**", "/consultas/actualizar/**", "/consultas/eliminar/**")
                    .hasAuthority("ADMINISTRADOR")
                .requestMatchers("/medicos/**")
                    .hasAuthority("ADMINISTRADOR")

                .requestMatchers("/api/medicos/**")
                    .hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/api/consultas/**")
                    .hasAnyAuthority("ADMINISTRADOR", "MEDICO", "PACIENTE")
                .requestMatchers(HttpMethod.PATCH, "/api/consultas/*/horario")
                    .hasAnyAuthority("ADMINISTRADOR", "PACIENTE")
                .requestMatchers("/api/**")
                    .hasAuthority("ADMINISTRADOR")

                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll())
            .httpBasic(Customizer.withDefaults())
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll())
            .exceptionHandling(ex -> ex
                .defaultAuthenticationEntryPointFor((request, response, e) -> {
                    response.setStatus(401);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"error\":\"Debe iniciar sesion para usar este servicio\"}");
                }, request -> esApi(request))
                .accessDeniedHandler((request, response, e) -> {
                    if (e instanceof CsrfException) {
                        response.sendRedirect(request.getContextPath() + "/login?csrf");
                        return;
                    }
                    if (esApi(request)) {
                        response.setStatus(403);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("{\"error\":\"Tu rol no tiene permiso para realizar esta accion\"}");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/403");
                    }
                }));

        return http.build();
    }

    private static boolean esApi(HttpServletRequest request) {
        return request.getRequestURI().startsWith(request.getContextPath() + "/api/");
    }
}
