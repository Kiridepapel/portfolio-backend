package xyz.kiridepapel.portfoliobackend.config;

import xyz.kiridepapel.portfoliobackend.dto.ResponseDTO;
import xyz.kiridepapel.portfoliobackend.exception.ExceptionConverter;
import xyz.kiridepapel.portfoliobackend.exception.JwtExceptions.*;
import xyz.kiridepapel.portfoliobackend.impl.JwtServiceImpl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtServiceImpl jwtServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        try {
            final String username;
            final String token = jwtServiceImpl.getTokenFromRequest(request);

            // Está iniciando sesión o registrándose
            if (token == null && isPublicUrl(request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            } else if (token == null) {
                throw new NoTokenException("No token provided");
            }

            // Token en blacklist
            if (jwtServiceImpl.isTokenBlacklisted(token)) {
                throw new BlacklistedTokenException("Token is blacklisted");
            }

            username = jwtServiceImpl.getUsernameFromToken(token);

            // Validar si el usuario existe
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Validar si el token es válido
                if (jwtServiceImpl.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // Continuar con la cadena de filtros
            filterChain.doFilter(request, response);
            
        } catch (NoTokenException ex) {
            ExceptionConverter.saveAndShowInfoError(ex, response, new ResponseDTO(ex.getMessage(), 401));
        } catch (BlacklistedTokenException ex) {
            ExceptionConverter.saveAndShowInfoError(ex, response, new ResponseDTO(ex.getMessage(), 401));
        } catch (SignatureException | MalformedJwtException ex) {
            ExceptionConverter.saveAndShowInfoError(ex, response, new ResponseDTO(ex.getMessage(), 401));
        } catch (ExpiredJwtException ex) {
            ExceptionConverter.saveAndShowInfoError(ex, response, new ResponseDTO(ex.getMessage(), 401));
        } catch (UsernameNotFoundException ex) {
            ExceptionConverter.saveAndShowInfoError(ex, response, new ResponseDTO(ex.getMessage(), 401));
        } catch (Exception ex) {
            ExceptionConverter.saveAndShowInfoError(ex, response, new ResponseDTO(ex.getMessage(), 500));
        }
    }

    // Si la url es pública, retorna true
    private boolean isPublicUrl(String url) {
        return url.startsWith("/api/auth");
    }

}
