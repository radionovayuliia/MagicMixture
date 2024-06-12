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
                            .requestMatchers("/magicmixture/**", "/magicmixture/registro/submit", "/autoregistro/**", "/styles/**", "/images/**", "/gif/**")
                            .permitAll()
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers("/valoracion/producto/**", "/valoracion/usuario/**", "/valoracion/new/**", "/valoracion/**", "/cocteles", "/magicmixture/carrito/**")
                            .hasAnyRole("USER", "MANAGER", "ADMIN")
                            .requestMatchers("/valoracion/new").hasRole("USER")
                            .requestMatchers("/valoracion/**", "/cocteles/**", "/categorias/**")
                            .hasAnyRole("MANAGER", "ADMIN")
                            .requestMatchers("/administracion")
                            .hasAnyRole( "ADMIN")
                            .anyRequest().authenticated())
                            .formLogin(formLogin -> formLogin
                                            .loginPage("/magicmixture/login")
                                            .defaultSuccessUrl("/cocteles", true)
                                            .permitAll())
                            .logout(logout -> logout
                                            .logoutUrl("/magicmixture/logout") 
                                            .logoutSuccessUrl("/magicmixture")
                                            .permitAll())
                            .rememberMe(Customizer.withDefaults())
                            .httpBasic(Customizer.withDefaults());
            http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/accessError"));
            return http.build();
    }
}
