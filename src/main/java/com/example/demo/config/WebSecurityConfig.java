package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((req) -> req
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/loginPro")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
            )
            .rememberMe((rememberMe) -> rememberMe
                .key("myWeb")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(86400)
            )
            .exceptionHandling((exceptionHandling) -> exceptionHandling
                .accessDeniedPage("/denied")
            )
            .sessionManagement((sessionManagement) -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .invalidSessionUrl("/login")
            )
            .csrf((AbstractHttpConfigurer::disable));

        return http.build();
    }

}
