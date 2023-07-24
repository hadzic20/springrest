package com.example.login;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository repository;
    public UserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String myemail)
            throws UsernameNotFoundException {
            List<myUser> liste = repository.findAll();
            for (com.example.login.myUser myUser : liste) {
                if (myUser.getEmail().equals(myemail)) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(authority);
                    return new User(myUser.getEmail(), new BCryptPasswordEncoder().encode(myUser.getPassword()), authorities);
                }
            }
            throw new UsernameNotFoundException("Kullanıcı bulunamadıı");
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}