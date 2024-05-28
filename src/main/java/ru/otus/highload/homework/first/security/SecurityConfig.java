package ru.otus.highload.homework.first.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.otus.highload.homework.first.service.UserDetail;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final UserDetail userDetail;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private final UnauthorizedEntrypoint unauthorizedEntrypoint;

    @Bean
    public SecurityFilterChain filterSecurity(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.securityMatcher("/user/register","/user/get/**","/login")
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers("/user/get/**").authenticated()
                                .requestMatchers("/user/register").permitAll()
                                .requestMatchers("/login").authenticated()
                                .anyRequest().authenticated())


                .httpBasic(httpSec -> httpSec.authenticationEntryPoint(unauthorizedEntrypoint)).userDetailsService(userDetail);
        return http.build();
    }
}
