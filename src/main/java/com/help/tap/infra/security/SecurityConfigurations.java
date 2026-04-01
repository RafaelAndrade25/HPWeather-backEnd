package com.help.tap.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfigurations {
    private final SecurityFilter securityFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                                .requestMatchers("/health").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole(
                                        "ADMIN", "DOCTOR", "POLICE", "FIREFIGHTER", "RESCUER"
                                )
                                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                                .requestMatchers("/api/users/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/wearables").hasAnyRole("PATIENT", "ADMIN")
                                .requestMatchers("/api/wearables/**").authenticated()
                                .requestMatchers("/api/addresses/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/emergency-contacts/**").hasAnyRole(
                                        "PATIENT", "ADMIN", "DOCTOR", "POLICE", "FIREFIGHTER", "RESCUER"
                                )
                                .requestMatchers("/api/emergency-contacts/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/illnesses/**").hasAnyRole(
                                        "PATIENT", "ADMIN", "DOCTOR", "FIREFIGHTER", "RESCUER"
                                )
                                .requestMatchers("/api/illnesses/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/deficiencies/**").hasAnyRole(
                                        "PATIENT", "ADMIN", "DOCTOR", "FIREFIGHTER", "RESCUER"
                                )
                                .requestMatchers("/api/deficiencies/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                                .requestMatchers("/api/access-logs/**").hasAnyRole(
                                        "ADMIN", "DOCTOR", "POLICE", "FIREFIGHTER", "RESCUER"
                                )
                                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
