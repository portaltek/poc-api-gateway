package portaltek.pagw.gateway;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import portaltek.pagw.common.web.security.jwt.*;
import portaltek.pagw.common.web.security.WebSecurityEntryPoint;


@Configuration
class JwtWebSecurityConfig {

   @Value("${jwt.header}")
   private String tokenHeader;
   @Value("${jwt.refresh.header}")
   private String refreshTokenHeader;
   @Value("${jwt.secret}")
   private String secret;
   @Value("${jwt.expiration}")
   private Long expiration;

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public ProfileServiceAdapter profileServiceAdapter() {
      return new ProfileServiceAdapter();
   }

   @Bean
   public WebSecurityEntryPoint unauthorizedHandler() {
      return new WebSecurityEntryPoint();
   }

   @Bean
   public JwtUtil jwtUtil() {
      return new JwtUtil(secret);
   }

   @Bean
   public JwtValidator tokenValidator(JwtUtil jwtUtil) {
      return new JwtValidator(jwtUtil, tokenHeader, refreshTokenHeader);
   }

   @Bean
   public JwtFilter tokenFilter(JwtValidator jwtValidator) {
      return new JwtFilter(jwtValidator);
   }

   @Bean
   public JwtGenerator jwtGenerator(ProfileServiceAdapter profileServiceAdapter) {
      return new JwtGeneratorImpl(secret, expiration, profileServiceAdapter);
   }


}
