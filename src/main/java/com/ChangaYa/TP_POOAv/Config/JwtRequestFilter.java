/*package com.ChangaYa.TP_POOAv.Config;

import com.ChangaYa.TP_POOAv.Config.CustomUserDetailsService;
import com.ChangaYa.TP_POOAv.Config.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String requestPath = request.getRequestURI();
    
    // Log para verificar la ruta que se está filtrando
    System.out.println("Request Path: " + requestPath);

    // Omitir el filtro para rutas públicas
    if (requestPath.equals("/api/register") || requestPath.equals("/api/login")) {
        System.out.println("Skipping JWT filter for public route: " + requestPath);
        chain.doFilter(request, response);
        return;
    }

    final String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        System.out.println("Authorization header is missing or does not start with Bearer");
    }

    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        jwt = authorizationHeader.substring(7);
        try {
            username = jwtUtil.extractUsername(jwt);
        } catch (Exception e) {
            System.out.println("Failed to extract username from JWT: " + e.getMessage());
        }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (jwtUtil.validateToken(jwt, userDetails)) {
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println("Authenticated user: " + username);
        } else {
            System.out.println("Token is invalid for user: " + username);
        }
    }

    chain.doFilter(request, response);
}

}
*/
