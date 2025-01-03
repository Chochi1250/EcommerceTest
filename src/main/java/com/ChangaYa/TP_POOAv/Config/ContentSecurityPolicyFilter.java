/*package com.ChangaYa.TP_POOAv.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ContentSecurityPolicyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' 'unsafe-inline';");
        filterChain.doFilter(request, response);
    }
}
     */