package cm.gelodia.uaa.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailService {
    UserDetails loadUserByUsernameAndCompanyCode(String usernameOrEmail, String companyCode) throws UsernameNotFoundException;
}
