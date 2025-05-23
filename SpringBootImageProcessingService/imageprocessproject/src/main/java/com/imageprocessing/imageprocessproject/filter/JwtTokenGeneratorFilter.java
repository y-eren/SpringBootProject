package com.imageprocessing.imageprocessproject.filter;

import com.imageprocessing.imageprocessproject.helper.TokenHolder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Environment env  = getEnvironment();

        if (null != env) {
            String secret = env.getProperty("jwt.secret", "GSqVmRbYXVuNDgzeXZxRU9HSjFHYVByT0ZBTlJDY0dCSGU=");
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder().issuer("Joseph").subject("JWT Token")
                    .claim("username", auth.getName())
                    .issuedAt(new Date())
                    .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 4))
                    .signWith(secretKey).compact();

            TokenHolder.setToken(jwt);
            response.setHeader("Authorization", jwt);

        }

        filterChain.doFilter(request, response);
    }




    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }
}
