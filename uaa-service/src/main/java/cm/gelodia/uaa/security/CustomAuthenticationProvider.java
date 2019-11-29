package cm.gelodia.uaa.security;

import cm.gelodia.uaa.security.model.CustomUserDetails;
import cm.gelodia.uaa.security.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider() {
    }

    @Autowired
    public CustomAuthenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String usernameOrEmail = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (!password.isEmpty() && !usernameOrEmail.isEmpty()){
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(usernameOrEmail);

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new  BadCredentialsException("Invalid password!");
            }
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        } else{
            throw new AuthenticationCredentialsNotFoundException("username or email and password are required!");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
