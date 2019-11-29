package cm.gelodia.proxy.filter;

import cm.gelodia.proxy.constant.ConstantTypes;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return ConstantTypes.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return ConstantTypes.LOGGING_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return ConstantTypes.SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        log.info("{} --- {}", request.getMethod(), request.getRequestURI());
        return null;
    }
}
