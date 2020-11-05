package portaltek.pagw.gateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import portaltek.pagw.common.web.security.JwtUtil;
import portaltek.pagw.common.web.security.TokenFilter;
import portaltek.pagw.common.web.security.TokenValidator;
import portaltek.pagw.common.web.security.WebSecurityEntryPoint;


@Configuration
class GatewayWebSecurityConfig {

   @Value("${jwt.header}")
   private String tokenHeader;
   @Value("${jwt.refresh.header}")
   private String refreshTokenHeader;
   @Value("${jwt.secret}")
   private String secret;

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   protected ProfileServiceAdapter profileServiceAdapter() {
      return new ProfileServiceAdapter();
   }

   @Bean
   protected WebSecurityEntryPoint unauthorizedHandler() {
      return new WebSecurityEntryPoint();
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
