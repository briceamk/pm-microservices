package cm.gelodia.uaa.security.login;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String companyCode;

    public CustomAuthenticationToken(Object principal, Object credentials, String companyCode) {
        super(principal, credentials);
        this.companyCode = companyCode;
        super.setAuthenticated(false);
    }

    public CustomAuthenticationToken(Object principal, Object credentials,
                                     Collection<? extends GrantedAuthority> authorities, String companyCode) {
        super(principal, credentials, authorities);
        this.companyCode = companyCode;
        super.setAuthenticated(true); // must use super, as we override
    }

    public String getCompanyCode() {
        return companyCode;
    }
}
