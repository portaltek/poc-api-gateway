package portaltek.pagw.common.web.security;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;


public class TokenValidator {

   private final Log logger = LogFactory.getLog(this.getClass());

   @Autowired
   private JwtUtil jwtUtil;

   @Value("${jwt.header}")
   private String tokenHeader;
   @Value("${jwt.refresh.header}")
   private String refreshTokenHeader;

   public TokenValidator(JwtUtil jwtUtil, String tokenHeader, String refreshTokenHeader) {
      this.jwtUtil = jwtUtil;
      this.tokenHeader = tokenHeader;
      this.refreshTokenHeader = refreshTokenHeader;
   }

   public void validate(HttpServletRequest request) {

      if (getContext().getAuthentication() != null) {
         return;
      }
      ofNullable(request.getHeader(tokenHeader))
         .filter(jwtUtil::hasToken)
         .map(jwtUtil::removePrefix)
         .ifPresent(e -> validateToken(request, e));

   }


   public void validateToken(HttpServletRequest request, String token) {

      var username = jwtUtil.getUsernameFromToken(token);
      var authorities = jwtUtil.getRolesFromToken(token);
      logger.info("checking authentication for user " + username);

      if (jwtUtil.isValid(token)) {
         setSecurityCtx(request, username, authorities);
      }

   }

   private void setSecurityCtx(HttpServletRequest request,
                               String username,
                               List<SimpleGrantedAuthority> authorities) {

      var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
      auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      logger.info("authenticated user " + username + ", setting security context");
      getContext().setAuthentication(auth);
   }


}