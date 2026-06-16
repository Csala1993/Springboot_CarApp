package com.carapp.carmaintenance.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                        CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoint-uri publice
                        .requestMatchers("/api/auth/**").permitAll()

                        // Administrare
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/users", "/api/users/**").hasRole("ADMIN")

                        // Liste globale, accesibile numai administratorului
                        .requestMatchers(HttpMethod.GET, "/api/masini").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/service").hasRole("ADMIN")

                        // PDF-ul asigurării trebuie să rămână disponibil utilizatorilor
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/asigurari/extract-pdf"
                        ).hasAnyRole("USER", "ADMIN")

                        // Controllere generale care expun toate înregistrările
                        .requestMatchers("/api/asigurari/**").hasRole("ADMIN")
                        .requestMatchers("/api/roviniete/**").hasRole("ADMIN")
                        .requestMatchers("/api/piese/**").hasRole("ADMIN")

                        // Operații făcute în contextul utilizatorului autentificat
                        .requestMatchers(HttpMethod.POST, "/api/masini")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/masini/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/service/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/investitii/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/notificari/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/piese-search/**")
                        .hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}