package cm.gelodia.uaa.security.service;

import cm.gelodia.uaa.model.User;
import cm.gelodia.uaa.repository.UserRepository;
import cm.gelodia.uaa.security.model.CustomUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService() {
    }
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(usernameOrEmail))
            throw new UsernameNotFoundException("Username or email and company code must provided!");
        User user = userRepository.getByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException(String.format("username or email!"))
        );
        CustomUserDetails userDetails = CustomUserDetails.create(user);
        new AccountStatusUserDetailsChecker().check(userDetails);
        return userDetails;
    }
}
