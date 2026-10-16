package br.com.projeto.chamados.security;

import com.projeto.estoque.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()

                        .requestMatchers("/funcionarios/**").hasRole("ADMIN")
                        .requestMatchers("/funcionarios/buscanome/**").hasRole("PADRAO")
                        .requestMatchers("/funcionarios/atualizanome/**").hasRole("PADRAO")
                        .requestMatchers("/funcionarios/buscaid/**").hasRole("PADRAO")
                        .requestMatchers("/funcionarios/todos/**").hasRole("PADRAO")
                        .requestMatchers("/compras/**").hasAnyRole("ADMIN","PADRAO")
                        .requestMatchers("/categorias/**").hasAnyRole("ADMIN","PADRAO")
                        .requestMatchers("/movimentacoes/**").hasAnyRole("ADMIN","PADRAO")
                        .requestMatchers("/estoques/**").hasAnyRole("ADMIN","PADRAO")
                        .requestMatchers("/fornecedores/**").hasAnyRole("ADMIN","PADRAO")
                        .requestMatchers("/produtos/**").hasAnyRole("ADMIN","PADRAO")






                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}