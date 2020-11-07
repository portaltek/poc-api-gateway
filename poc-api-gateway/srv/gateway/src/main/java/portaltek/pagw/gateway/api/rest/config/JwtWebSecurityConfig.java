package portaltek.pagw.gateway.api.rest.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import portaltek.pagw.common.GenericJwtWebSecurityConfig;
import portaltek.pagw.common.web.security.jwt.JwtProps;


@Configuration
class JwtWebSecurityConfig extends GenericJwtWebSecurityConfig {

   @Value("${jwt.header}")
   private String tokenHeader;
   @Value("${jwt.refresh.header}")
   private String refreshTokenHeader;
   @Value("${jwt.secret}")
   private String secret;
   @Value("${jwt.expiration}")
   private Long expiration;

   @Bean
   public JwtProps jwtProps() {
      return new JwtProps(tokenHeader,
         refreshTokenHeader,
         secret,
         expiration);
   }


}
