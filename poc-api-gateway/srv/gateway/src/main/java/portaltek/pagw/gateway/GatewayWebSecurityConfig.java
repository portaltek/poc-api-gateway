package portaltek.pagw.gateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import portaltek.pagw.common.web.security.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
class GatewayWebSecurityConfig extends AbstractWebSecurityConfig {


   @Autowired
   protected UserDetailsService userDetailsService;
   @Value("${jwt.header}")
   private String tokenHeader;
   @Value("${jwt.refresh.header}")
   private String refreshTokenHeader;
   @Value("${jwt.secret}")
   private String secret;

   @Bean
   protected WebSecurityEntryPoint unauthorizedHandler() {
      return new WebSecurityEntryPoint();
   }

   @Bean
   protected UserDetailsService userDetailsService() {
      return userDetailsService;
   }


   @Bean
   protected JwtUtil jwtUtil() {
      return new JwtUtil(secret);
   }

   @Bean
   protected TokenValidator tokenValidator(JwtUtil jwtUtil) {
      return new TokenValidator(jwtUtil, tokenHeader, refreshTokenHeader);
   }

   @Bean
   protected TokenFilter tokenFilter(TokenValidator tokenValidator) {
      return new TokenFilter(tokenValidator);
   }




}
