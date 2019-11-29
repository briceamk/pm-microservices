package cm.gelodia.uaa.security.service;

import cm.gelodia.uaa.model.User;
import cm.gelodia.uaa.repository.UserRepository;
import cm.gelodia.uaa.security.model.CustomUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class CustomUserDetailsServiceImpl implements CustomUserDetailService {

    private UserRepository userRepository;

    public CustomUserDetailsServiceImpl() {
    }
    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsernameAndCompanyCode(String usernameOrEmail, String companyCode) throws UsernameNotFoundException {
        //TODO We consider here the id of the society like the code then one will implement the version by searching with the id
        if(StringUtils.isAnyEmpty(usernameOrEmail, companyCode))
            throw new UsernameNotFoundException("Username or email and company code must provided!");
        User user = userRepository.getByUsernameOrEmailAndCompanyId(usernameOrEmail, usernameOrEmail, companyCode).orElseThrow(
                () -> new UsernameNotFoundException(String.format("username or email %s not found for company code !", usernameOrEmail, companyCode))
        );
        CustomUserDetails userDetails = CustomUserDetails.create(user);
        new AccountStatusUserDetailsChecker().check(userDetails);
        return userDetails;
    }
}
