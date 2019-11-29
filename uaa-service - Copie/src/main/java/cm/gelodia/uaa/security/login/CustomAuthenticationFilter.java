package cm.gelodia.uaa.security.login;

import cm.gelodia.uaa.constant.ConstantTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("Authentication Filter ******************************");
        System.out.println("Authentication Filter ******************************");
        if(!request.getMethod().equals(ConstantTypes.METHOD_POST))
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
       CustomAuthenticationToken authRequest = getAuthRequest(request);
       setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private CustomAuthenticationToken getAuthRequest(HttpServletRequest request) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String companyCode = obtainCompanyCode(request);
        if (username.equals(null))
            username = "";
        if (password.equals(null))
            password = "";
        if (companyCode.equals(null))
            companyCode = "";
        return new CustomAuthenticationToken(username, password,companyCode);
    }

    private String obtainCompanyCode(HttpServletRequest request) {
        return request.getParameter(ConstantTypes.COMPANY_CODE);
    }
}
