package com.company.jetassesment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigurationBasicAuth {

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    //dGVzdHVzZXI6dGVzdHBhc3N3b3Jk

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("testuser")
                .password(encoder().encode("testpassword"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http.authorizeHttpRequests((authz) -> authz
                                .requestMatchers(HttpMethod.GET, "/employees/**").permitAll()
                                .requestMatchers( "/employees/**").hasRole("USER")
                                .anyRequest().authenticated()

                )
                .csrf((csrf) -> csrf.disable())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}