package com.imageprocessing.imageprocessproject.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/user", "/images"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");

        if (jwt != null ) {
            try {
                Environment env = getEnvironment();
                if (env != null) {
                    String secret = env.getProperty("jwt.secret", "GSqVmRbYXVuNDgzeXZxRU9HSjFHYVByT0ZBTlJDY0dCSGU=");

                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                    if(secretKey != null) {
                        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
                        String username = String.valueOf(claims.get("username"));

                        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            } catch(Exception ex) {
                throw new BadCredentialsException("Invalid Token Alindi" + ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
         return request.getServletPath().equals("/user");
//        String path = request.getServletPath();
//        return EXCLUDED_PATHS.contains(path);
    }
}
