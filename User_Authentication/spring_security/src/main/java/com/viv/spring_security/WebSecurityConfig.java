package com.viv.spring_security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//enable Spring Security’s web security support and provide the Spring MVC integration.
//It also exposes two beans to set some specifics for the web security configuration:
public class WebSecurityConfig {

    @Bean
    //The SecurityFilterChain bean defines which URL paths should be secured and which should not.
    // Specifically, the / and /home paths are configured to not require any authentication.
    // All other paths must be authenticated.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated()
                )
                //When a user successfully logs in, they are redirected to the previously requested page that required authentication.
                //There is a custom /login page (which is specified by loginPage()), and everyone is allowed to view it.
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }
    @Bean
    //The UserDetailsService bean sets up an in-memory user store with a single user.
    // That user is given a user name of user, a password of password, and a role of USER.
    public UserDetailsService userDetailsService(){
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }
}
