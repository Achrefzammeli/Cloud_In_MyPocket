package tn.esprit.cloud_in_mypocket.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Active CORS
                .cors(withDefaults())
                // Désactive CSRF (attention en production)
                .csrf(csrf -> csrf.disable())
                // Configure les règles d'autorisation
                .authorizeHttpRequests(auth -> auth
                        // Autorise toutes les requêtes OPTIONS (pour le prévol CORS)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Autorise l'inscription et l'authentification
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()

                        .requestMatchers("/api/users/register", "/api/auth/**").permitAll()
                        // Autorise les endpoints de test
                        .requestMatchers("/api/test/**").permitAll()
                        // Autorise toutes les requêtes GET sur /api/users et ses sous-chemins
                        .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/packs/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/packs/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/packs/**").permitAll()
                        // Autorise toutes les requêtes DELETE sur /api/users et ses sous-chemins
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/packs/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").permitAll()
                        // Toute autre requête doit être authentifiée
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}