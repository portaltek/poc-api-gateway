package portaltek.pagw.common;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import portaltek.pagw.common.web.security.WebSecurityEntryPoint;
import portaltek.pagw.common.web.security.jwt.JwtUserFactory;


@Configuration
public class JwtValidatorWebSecurityConfig {

   @Bean
   public WebSecurityEntryPoint unauthorizedHandler() {
      return new WebSecurityEntryPoint();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public JwtUserFactory jwtUserFactory(PasswordEncoder encoder) {
      return new JwtUserFactory(encoder);
   }

}
