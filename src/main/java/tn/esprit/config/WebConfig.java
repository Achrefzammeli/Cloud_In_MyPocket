package tn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Applique les règles CORS uniquement sur les routes commençant par /api
                .allowedOrigins("http://localhost:4200")  // Permet uniquement le frontend Angular sur le port 4200
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Méthodes HTTP autorisées
                .allowedHeaders("*");  // Autorise tous les en-têtes
    }
}
