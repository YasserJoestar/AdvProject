package com.example.simpleportal;

import com.example.simpleportal.Model.Student;
import com.example.simpleportal.Service.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final StudentRepository studentRepository;

    public SecurityConfig(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/login", "/signup", "/h2-console/**", "/css/**", "/js/**", "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                org.springframework.security.core.Authentication authentication)
                    throws IOException, ServletException {
                String username = authentication.getName();
                Student student = studentRepository.findByEmailIgnoreCase(username)
                        .orElse(null);
                if (student != null) {
                    addCookie(response, "student_email", student.getEmail(), 7 * 24 * 60 * 60);
                    addCookie(response, "student_id", String.valueOf(student.getId()), 7 * 24 * 60 * 60);
                    addCookie(response, "student_role", student.getRole(), 7 * 24 * 60 * 60);
                }
                response.sendRedirect("/dashboard");
            }
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        org.springframework.security.core.Authentication authentication)
                    throws IOException, ServletException {
                deleteCookie(response, "student_email");
                deleteCookie(response, "student_id");
                deleteCookie(response, "student_role");
                response.sendRedirect("/login?logout=true");
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Student student = studentRepository.findByEmailIgnoreCase(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Student not found: " + username));
            UserDetails user = User.withUsername(student.getEmail())
                    .password(student.getPassword())
                    .authorities("ROLE_" + student.getRole())
                    .build();
            return user;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        String encodedValue = value == null ? "" : value;
        response.addHeader("Set-Cookie", name + "=" + encodedValue + "; Max-Age=" + maxAge + "; Path=/; SameSite=Lax");
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        response.addHeader("Set-Cookie", name + "=; Max-Age=0; Path=/; SameSite=Lax");
    }
}
