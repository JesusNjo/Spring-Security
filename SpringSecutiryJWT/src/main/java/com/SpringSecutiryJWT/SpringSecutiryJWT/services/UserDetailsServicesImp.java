package com.SpringSecutiryJWT.SpringSecutiryJWT.services;

import com.SpringSecutiryJWT.SpringSecutiryJWT.models.UserEntity;
import com.SpringSecutiryJWT.SpringSecutiryJWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServicesImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userDetails = userRepository.findByusername(username).orElseThrow(()-> new UsernameNotFoundException("El usuario ".concat(username).concat(" no existe")));

        Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getRoles().stream().map(r->
                new SimpleGrantedAuthority("ROLE_".concat(r.getName().name()))
        ).collect(Collectors.toSet());
        return new User(userDetails.getUsername(),
                userDetails.getPassword(),true,true,true,true,grantedAuthorities);
    }
}
