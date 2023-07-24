package com.example.login;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider {

    private final UserDetailsService usrsrvc;
    public AuthProvider(UserRepository repository) {
        usrsrvc = new UserDetailsService(repository);
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        UserDetails user = usrsrvc.loadUserByUsername(username);
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
        }
        throw new BadCredentialsException("Error!!");
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.equals(authenticationType);
    }

}