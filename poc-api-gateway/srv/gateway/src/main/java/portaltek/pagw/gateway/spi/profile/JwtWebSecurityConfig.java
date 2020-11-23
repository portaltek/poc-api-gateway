package portaltek.pagw.gateway.spi.profile;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import portaltek.pagw.common.web.security.GenericJwtWebSecurityConfig;
import portaltek.pagw.common.web.security.jwt.JwtProps;
import portaltek.pagw.common.web.security.jwt.JwtUserFactory;

import static portaltek.pagw.common.env.AppProfile.*;


public class JwtWebSecurityConfig extends GenericJwtWebSecurityConfig {

   @Value("${jwt.header}")
   private String jwtHeader;
   @Value("${jwt.refresh.header}")
   private String refreshJwtHeader;
   @Value("${jwt.secret}")
   private String secret;
   @Value("${jwt.expiration}")
   private Long expiration;

   @Bean
   public JwtProps jwtProps() {
      return new JwtProps(jwtHeader, refreshJwtHeader, secret, expiration);
   }


   @Profile({DEV, QA, STG, PROD})
   @Bean
   public UserDetailsService userDetailsService(JwtUserFactory jwtUserFactory) {
      return new LocalProfileServiceAdapter(jwtUserFactory);
   }

   @Profile(LOCAL)
   @Bean
   public UserDetailsService localUserDetailsService(JwtUserFactory jwtUserFactory) {
      System.out.println("localUserDetailsService");
      return new LocalProfileServiceAdapter(jwtUserFactory);
   }


}
