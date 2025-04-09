package com.example.movie.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.movie.Service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            com.example.movie.Model.User user = userService.findByUsername(username);
            if (user == null) {
                throw new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found");
            }
            if (!user.isActive()) {
                throw new org.springframework.security.authentication.DisabledException("User is not active");
            }
            return User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .disabled(!user.isActive())
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**", "/admin/users/**", "/admin/movies/**", "/admin/tickets/**").hasRole("ADMIN")
                .requestMatchers("/", "/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    if (authentication.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        response.sendRedirect("/admin/dashboard");
                    } else {
                        response.sendRedirect("/");
                    }
                })
                .failureHandler((request, response, exception) -> {
                    response.sendRedirect("/login?error");
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .authenticationProvider(authenticationProvider());
        
        return http.build();
    }
}