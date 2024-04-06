package com.nhom29.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class Security {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf( c -> c.disable())
                .cors( c->c.disable())
                .authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/home").hasAuthority("USER")
                        .requestMatchers("/oauth2/authorization/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(
                        f -> f.loginPage("/login")
                                .loginProcessingUrl("/sign-in")
                                .defaultSuccessUrl("/home", true)
                )
                .logout(
                        l -> l.invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")
                )
                .oauth2Login(
                        o -> o.loginPage("/login")
                                .defaultSuccessUrl("/home", true)
                )
                .exceptionHandling( ex -> ex.accessDeniedPage("/404"));
        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        UserDetails quochoai = User.builder()
                .username("hoai")
                .password("{noop}123")
                .authorities("USER")
                .build();
        return new InMemoryUserDetailsManager(quochoai);
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->
                web.ignoring()
                        .requestMatchers("/js/**", "/css/**");
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
