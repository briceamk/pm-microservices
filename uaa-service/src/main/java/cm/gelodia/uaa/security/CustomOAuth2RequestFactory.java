package cm.gelodia.uaa.security;

import cm.gelodia.uaa.constant.ConstantTypes;
import cm.gelodia.uaa.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Map;

public class CustomOAuth2RequestFactory extends DefaultOAuth2RequestFactory {

    @Autowired
    private TokenStore tokenStore;
    private UserDetailsService userDetailsService;

    public CustomOAuth2RequestFactory(ClientDetailsService clientDetailsService) {
        super(clientDetailsService);
    }

    public CustomOAuth2RequestFactory(ClientDetailsService clientDetailsService,
                                      CustomUserDetailsService userDetailsService, TokenStore tokenStore){
        super(clientDetailsService);
        this.userDetailsService = userDetailsService;
        this.tokenStore = tokenStore;
    }

    @Override
    public TokenRequest createTokenRequest(Map<String, String> requestParameters, ClientDetails authenticatedClient) {
        if(requestParameters.get(ConstantTypes.GRANT_TYPE_KEY).equals(ConstantTypes.REFRESH_TOKEN)) {
            OAuth2Authentication authentication = tokenStore.readAuthenticationForRefreshToken(
                    tokenStore.readRefreshToken(requestParameters.get(ConstantTypes.REFRESH_TOKEN))
            );
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(authentication.getName(), null,
                            userDetailsService.loadUserByUsername(authentication.getName()).getAuthorities()));
        }
        return super.createTokenRequest(requestParameters, authenticatedClient);
    }
}
