package mk.ukim.finki.izborzvanje.config;

import mk.ukim.finki.izborzvanje.model.Enumerations.AppRole;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

public class AuthConfig {

    public HttpSecurity authorize(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/Criteria/**").hasAnyRole(
                                AppRole.PROFESSOR.name(),
                                AppRole.ADMIN.name()
                        )
                        .anyRequest().authenticated()
                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
                .logout(LogoutConfigurer::permitAll);
    }

}
