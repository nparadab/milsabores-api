package com.milsabores.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtFilter = new JwtAuthFilter(jwtUtil);

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // 🔓 Registro y login sin token
                .requestMatchers("/api/auth/**").permitAll()

                // 👁️ CLIENTE y ADMIN pueden ver productos y categorías
                .requestMatchers(HttpMethod.GET, "/api/productos/**", "/api/categorias/**").hasAnyRole("ADMIN", "CLIENTE")

                // ✏️ Solo ADMIN puede modificar productos y categorías
                .requestMatchers(HttpMethod.POST, "/api/productos/**", "/api/categorias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/productos/**", "/api/categorias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**", "/api/categorias/**").hasRole("ADMIN")

                // 📦 Pedidos accesibles por ambos
                .requestMatchers("/api/pedidos/**").hasAnyRole("ADMIN", "CLIENTE")

                // 🔐 Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
