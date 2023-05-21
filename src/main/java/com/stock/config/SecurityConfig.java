package com.stock.config;

import com.stock.security.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final String MAIN_ENDPOINT = "/api/v1/main";
    private static final String ABOUT_ENDPOINT = "/api/v1/about";
    private static final String AUTH_ENDPOINT = "/api/v1/auth/sign-in";
    private static final String AUTH_ENDPOINTV1 = "/api/v1/auth/refresh-token";
    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JwtTokenFilter jwtTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers(MAIN_ENDPOINT, AUTH_ENDPOINT,AUTH_ENDPOINTV1, "/api/v1/coins", "/api/v1/coins/*")
                .permitAll()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
