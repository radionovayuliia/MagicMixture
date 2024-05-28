package com.example.demo.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.headers(headersConfigurer -> headersConfigurer
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/**", "/cocteles", "/categorias", "/autoregistro/**", "/h2-console/**")
                                .permitAll() // configurarpermisosreales
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/valoracion/producto/**", "/valoracion/usuario/**", "/valoracion/new/**", "/valoracion/**")
                                .hasAnyRole("USER", "MANAGER", "ADMIN")
                                .requestMatchers("/valoracion/new").hasRole("USER")
                                .requestMatchers("/valoracion/**", "/cocteles/**", "/categorias/**")
                                .hasAnyRole("MANAGER", "ADMIN")
                                // .requestMatchers("/**").hasRole("ADMIN")
                                // para rutas: /css, /js /images
                                .anyRequest().authenticated())
                                .formLogin(formLogin -> formLogin
                                                .defaultSuccessUrl("/api", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/api")
                                                .permitAll())
                                // .csrf(csrf -> csrf.disable())
                                .rememberMe(Customizer.withDefaults())
                                .httpBasic(Customizer.withDefaults());
                http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/accessError"));
                return http.build();
        }
}
