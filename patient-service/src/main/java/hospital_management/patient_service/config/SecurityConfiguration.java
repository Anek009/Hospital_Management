package hospital_management.patient_service.config;

import hospital_management.patient_service.security.JwtAuthenticationFilter;
import hospital_management.patient_service.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/patient/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/patient/login").permitAll()
                        .requestMatchers("/api/patient/profile/get/all").hasRole(AppConstant.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/patient/profile/getByUserId").hasAnyRole(AppConstant.ROLE_ADMIN, AppConstant.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/patient/profile/search/**").hasRole(AppConstant.ROLE_PATIENT)
                        .requestMatchers("/api/patient/health/**").hasRole(AppConstant.ROLE_PATIENT)
                        .requestMatchers("/api/patient/proxy/**").permitAll()
                        .requestMatchers("/actuator/*").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
