package com.example.blogpostapplication.security.jwt;

import com.example.blogpostapplication.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/** This class makes a single execution for each request */
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {


   private final UserDetailsServiceImpl userDetailsService;


  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String jwt = parseJwt(request);

    if (jwt != null && JwtUtils.validateJwtToken(jwt)) {
      if (isLogoutRequest(request)) {
        JwtUtils.invalidateToken(jwt);
      } else if (!JwtUtils.isTokenInvalidated(jwt)) {
        String username = JwtUtils.getUserNameFromJwtToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } else {
      logger.debug("JWT token is invalid or missing");
    }

    filterChain.doFilter(request, response);

  }

  public String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }

  private boolean isLogoutRequest(HttpServletRequest request) {
    return request.getMethod().equals("POST") && request.getRequestURI().equals("/auth/logout");
  }
}
