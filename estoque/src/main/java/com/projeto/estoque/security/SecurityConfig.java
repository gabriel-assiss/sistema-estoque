
package com.projeto.estoque.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(
                List.of("http://localhost:5174")
        );

        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        config.setAllowedHeaders(
                List.of("Authorization", "Content-Type")
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {

        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/login").permitAll()

                        // Rotas específicas de funcionários
                        .requestMatchers(
                                "/funcionarios/buscanome/**",
                                "/funcionarios/salvar/**",
                                "/funcionarios/atualizanome/**",
                                "/funcionarios/buscaid/**",
                                "/funcionarios/todos/**"
                        ).hasAnyRole("ADMIN", "PADRAO")

                        // Demais operações de funcionários
                        .requestMatchers("/funcionarios/**").hasRole("ADMIN")

                        .requestMatchers("/compras/**").hasAnyRole("ADMIN", "PADRAO")
                        .requestMatchers("/categorias/**").hasAnyRole("ADMIN", "PADRAO")
                        .requestMatchers("/movimentacoes/**").hasAnyRole("ADMIN", "PADRAO")
                        .requestMatchers("/estoques/**").hasAnyRole("ADMIN", "PADRAO")
                        .requestMatchers("/fornecedores/**").hasAnyRole("ADMIN", "PADRAO")
                        .requestMatchers("/produtos/**").hasAnyRole("ADMIN", "PADRAO")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }
}
