package cm.gelodia.uaa.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class StatelessSecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
    public StatelessSecurityApplicationInitializer() {
        super(WebSecurityConfig.class);
    }
}
