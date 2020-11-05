package portaltek.pagw.common.web.security.jwt;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {

   private final Log logger = LogFactory.getLog(this.getClass());

   private JwtValidator validator;

   public JwtFilter(JwtValidator validator) {
      this.validator = validator;
   }

   @Override
   protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {

      addResponseHeaders(response);
      validator.validate(request);
      chain.doFilter(request, response);
   }

   private void addResponseHeaders(HttpServletResponse response) {
      response.addHeader("Access-Control-Allow-Headers",
         "Access-Control-Allow-Origin, Origin, Accept, X-Requested-With, Authorization, refreshauthorization, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Credentials");
      if (response.getHeader("Access-Control-Allow-Origin") == null)
         response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
      if (response.getHeader("Access-Control-Allow-Credentials") == null)
         response.addHeader("Access-Control-Allow-Credentials", "true");
      if (response.getHeader("Access-Control-Allow-Methods") == null)
         response.addHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
   }


}