package com.TyreseMv.TinyUrl.config;

import com.TyreseMv.TinyUrl.users.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager defaultUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin@email.com")
                        .password("{noop}password")
                        .authorities("read", "write")
                        .build()
        );
    }
    @Autowired
    private OAuth2LoginSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF (not recommended for forms)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**").permitAll()  // Allow static files
                        .requestMatchers("/dashboard/**").authenticated()
                        .requestMatchers("/").permitAll() // Public access
                        .anyRequest().permitAll() // Any other request requires authentication
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page
                        .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
                        .permitAll() // Allow everyone to access login page
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Custom login page
                        .successHandler(successHandler)
                        .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
                        .permitAll()
                ) // Allow everyone to access login page)
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Logout URL
                        .logoutSuccessUrl("/") // Redirect after logout
                        .permitAll()
                ).build();
    }
}
