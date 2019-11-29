package cm.gelodia.uaa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exc) throws IOException, ServletException {
        Map<String, Object> mapBodyException = new LinkedHashMap<>() ;

        mapBodyException.put("error"    , "UnAuthorized") ;
        mapBodyException.put("message"  , "You must login to access this resource") ;
        mapBodyException.put("status", HttpServletResponse.SC_UNAUTHORIZED) ;
        mapBodyException.put("path"     , request.getRequestURI()) ;
        mapBodyException.put("timestamp", LocalDateTime.now().toString()) ;
        mapBodyException.put("method", request.getMethod());

        response.setContentType("application/json") ;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;

        ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), mapBodyException) ;
    }
}
