package com.example.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*http.csrf().disable().authorizeHttpRequests(requests -> requests.requestMatchers("/", "/login", "/user", "/register", "/forgot").permitAll().anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").successForwardUrl("/login_success").failureForwardUrl("/login_failure").permitAll())
                .logout(LogoutConfigurer::permitAll);*/
        http.csrf().disable().authorizeHttpRequests(requests -> requests.requestMatchers("/secret", "/logout", "/profile", "/password", "/delete").authenticated().anyRequest().permitAll())
                .formLogin(form -> form.loginPage("/login").successForwardUrl("/login_success").failureForwardUrl("/login_failure").permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}
