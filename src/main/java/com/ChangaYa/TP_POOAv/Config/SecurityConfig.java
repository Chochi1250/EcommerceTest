package com.ChangaYa.TP_POOAv.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = auth.build();

        http
            .csrf(csrf -> csrf.disable())
            .authorizeRequests(authorize -> authorize
                .requestMatchers("/login", "/register", "/register/save", "/css/*", "/js/*").permitAll()
                .requestMatchers("/servicios", "/servicios/new").hasRole("USER")
                .requestMatchers("servicios/{servicioId}/eliminar").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login") // Página personalizada de login
                .loginProcessingUrl("/login") // URL que procesa el formulario de login
                .defaultSuccessUrl("/home", true) // Redirección tras login exitoso
                .failureUrl("/login?error=true") // Redirección con mensaje de error
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .authenticationManager(authenticationManager); // Configuración del AuthenticationManager

        return http.build();
    }


    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
