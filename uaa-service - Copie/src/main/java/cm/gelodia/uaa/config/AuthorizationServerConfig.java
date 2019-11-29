package cm.gelodia.uaa.config;

import cm.gelodia.uaa.security.CustomOAuth2RequestFactory;
import cm.gelodia.uaa.security.service.CustomUserDetailService;
import cm.gelodia.uaa.security.service.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${check.user.scopes}")
    private Boolean checkUserScopes;
    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetailService userDetailsService;
    private AuthenticationManager authenticationManager;
    private TokenStore tokenStore;
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    private ClientDetailsService clientDetailsService;

    @Autowired
    public AuthorizationServerConfig(
            DataSource dataSource,
            PasswordEncoder passwordEncoder,
            CustomUserDetailsServiceImpl userDetailsService,
            @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
            TokenStore tokenStore,
            JwtAccessTokenConverter jwtAccessTokenConverter,
            ClientDetailsService clientDetailsService
    ) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenStore = tokenStore;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.clientDetailsService = clientDetailsService;
    }

    @Bean
    public OAuth2RequestFactory requestFactory() {
        CustomOAuth2RequestFactory requestFactory = new CustomOAuth2RequestFactory(clientDetailsService);
        //requestFactory.setCheckUserScopes(true);
        return requestFactory;
    }

    @Bean
    public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter() {
        return new TokenEndpointAuthenticationFilter(authenticationManager, requestFactory());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .tokenEnhancer(jwtAccessTokenConverter)
                .authenticationManager(authenticationManager)
                .requestFactory(requestFactory());
    }

}
