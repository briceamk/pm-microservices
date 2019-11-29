package cm.gelodia.uaa.security.login;

import cm.gelodia.uaa.constant.ConstantTypes;
import cm.gelodia.uaa.security.service.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@Slf4j
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private PasswordEncoder passwordEncoder;
    private CustomUserDetailService userDetailService;
    private String userNotFoundEncodedPassword;

    @Autowired
    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, CustomUserDetailService userDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException {

        if(authentication.getCredentials() == null ) {
            log.debug("Authentication failed: no credential provided");
            throw new BadCredentialsException(
                    messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String presentedPassword = authentication.getCredentials().toString();

        if(!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            log.info("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(
                    messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailService, "A userDetailsService must be set");
        this.userNotFoundEncodedPassword = this.passwordEncoder.encode(ConstantTypes.USER_NOT_FOUND_PASSWORD);
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException{
        CustomAuthenticationToken auth = (CustomAuthenticationToken) authentication;
        UserDetails loadedUser;

        try {
            loadedUser = this.userDetailService.loadUserByUsernameAndCompanyCode(auth.getPrincipal().toString(), auth.getCompanyCode());
        } catch (UsernameNotFoundException notFound) {
            if(!authentication.getCredentials().equals(null)) {
                String presentedPassword = authentication.getCredentials().toString();
                passwordEncoder.matches(presentedPassword, userNotFoundEncodedPassword);
            }
            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(repositoryProblem.getLocalizedMessage(), repositoryProblem);
        }

        if(loadedUser.equals(null))
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        return loadedUser;
    }
}
