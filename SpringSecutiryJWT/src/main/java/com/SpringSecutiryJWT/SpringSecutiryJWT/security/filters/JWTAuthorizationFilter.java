package com.SpringSecutiryJWT.SpringSecutiryJWT.security.filters;

import com.SpringSecutiryJWT.SpringSecutiryJWT.security.JWT.JwtUtils;
import com.SpringSecutiryJWT.SpringSecutiryJWT.services.UserDetailsServicesImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UserDetailsServicesImp userDetailsServicesImp;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {


        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7,tokenHeader.length());

            if(jwtUtils.isTokenValid(token)){
                String username = jwtUtils.getUsernameFromToker(token);
                UserDetails userDetails = userDetailsServicesImp.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username , null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);

    }
}
