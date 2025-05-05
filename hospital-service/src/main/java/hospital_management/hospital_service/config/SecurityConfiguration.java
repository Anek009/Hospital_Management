package hospital_management.hospital_service.config;

import hospital_management.hospital_service.security.JwtAuthenticationFilter;
import hospital_management.hospital_service.util.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/hms/register/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/hms/login/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/hms/admin/department/getAllDept").permitAll()
                        .requestMatchers("/api/hms/admin/department/**").hasRole(AppConstant.ROLE_ADMIN)
                        .requestMatchers("/api/hms/admin/rooms/**").hasRole(AppConstant.ROLE_ADMIN)
                        .requestMatchers("/api/hms/admin/dashboard/**").hasRole(AppConstant.ROLE_ADMIN)
                        .requestMatchers("/api/hms/admin/proxy/**").permitAll()
                        .requestMatchers("/api/hms/doctor/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

}