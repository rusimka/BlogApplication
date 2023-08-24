package com.example.blogpostapplication.security;

import com.example.blogpostapplication.security.jwt.AuthEntryPointJwt;
import com.example.blogpostapplication.security.jwt.AuthTokenFilter;
import com.example.blogpostapplication.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

  @Autowired private UserDetailsServiceImpl userDetailsService;

  @Autowired private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(new AntPathRequestMatcher("/auth/**"))
                    .permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/blog-post"))
                    .permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/blog-post/create-blog-post"))
                    .permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/blog-post/get-all-blog-posts"))
                    .permitAll()
                    .requestMatchers(
                        new AntPathRequestMatcher("/blog-post/update-title-and-text/{blogPostId}"))
                    .permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/blog-post/add-tags/{blogPostId}"))
                    .permitAll()
                    .requestMatchers(
                        new AntPathRequestMatcher("/blog-post/delete-tag/{blogPostId}"))
                    .permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                    .permitAll() // Allow access to H2 Console
                    .anyRequest()
                    .authenticated())
        .headers(
            headers ->
                headers
                    .frameOptions()
                    .sameOrigin()); // Disable X-Frame-Options to allow the H2 Console in iframe

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(
        authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build(); // Return the SecurityFilterChain
  }
}
