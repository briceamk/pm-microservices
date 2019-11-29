package cm.gelodia.uaa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle( HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {
        Map<String, Object> mapBodyException = new LinkedHashMap<>() ;

        mapBodyException.put("error"    , "Forbidden") ;
        mapBodyException.put("message"  , "You have no rights to access this resource") ;
        mapBodyException.put("status", HttpServletResponse.SC_UNAUTHORIZED) ;
        mapBodyException.put("path"     , request.getRequestURI()) ;
        mapBodyException.put("method", request.getMethod());
        mapBodyException.put("timestamp", LocalDateTime.now().toString()) ;

        response.setContentType("application/json") ;
        response.setStatus(HttpServletResponse.SC_FORBIDDEN) ;

        ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), mapBodyException) ;
    }
}