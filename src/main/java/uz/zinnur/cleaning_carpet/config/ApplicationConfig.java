package uz.zinnur.cleaning_carpet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.zinnur.cleaning_carpet.auditing.ApplicationAuditAware;
import uz.zinnur.cleaning_carpet.service.CustomUserDetailsService;

import java.util.List;
import java.util.UUID;

@Configuration
public class ApplicationConfig {
    private final CustomUserDetailsService customUserDetailsService;

    public ApplicationConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(customUserDetailsService);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("auditor")
    AuditorAware<UUID> auditorProvider() {
        return new ApplicationAuditAware();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Specify allowed origins - avoid using "*" in production
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8080")); // Add trusted domains
        configuration.addAllowedOriginPattern("*");

        // Specify allowed HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Allow only required methods

        // Specify allowed headers
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Only allow specific headers

        // Expose specific headers
        configuration.setExposedHeaders(List.of("Authorization")); // Allow the frontend to access these headers

        // Allow credentials (cookies or authorization headers)
        configuration.setAllowCredentials(true);

        // Avoid using addAllowedOriginPattern("*") unless absolutely necessary
        // Only use it in development if you can't predict origins
        // configuration.addAllowedOriginPattern("*"); // Commented out to enhance security

        // Register the configuration for all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}

