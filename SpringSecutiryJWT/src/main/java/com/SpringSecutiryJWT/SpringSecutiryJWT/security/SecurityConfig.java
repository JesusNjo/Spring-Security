package com.SpringSecutiryJWT.SpringSecutiryJWT.security;


import com.SpringSecutiryJWT.SpringSecutiryJWT.security.JWT.JwtUtils;
import com.SpringSecutiryJWT.SpringSecutiryJWT.security.filters.JWTAuthenticationFilter;
import com.SpringSecutiryJWT.SpringSecutiryJWT.security.filters.JWTAuthorizationFilter;
import com.SpringSecutiryJWT.SpringSecutiryJWT.services.UserDetailsServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    UserDetailsServicesImp userDetails;

    @Autowired
    JwtUtils jwtUtils;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        //jwtAuthenticationFilter.setFilterProcessesUrl("/login"); -- Para cambiar la ruta

        return httpSecurity
                .csrf(config-> config.disable())
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/hello").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

   /* @Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("Jesusnjo")
                .password("9401").roles().build());

        return manager;
    }*/

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
    return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetails).passwordEncoder(passwordEncoder).and().build();
    }

  /* public static void main(String[] args){
        System.out.println(new BCryptPasswordEncoder().encode("1234"));
    }*/
}
