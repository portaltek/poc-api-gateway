package portaltek.pagw.gateway.spi.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import portaltek.pagw.common.web.security.jwt.JwtUserFactory;

import static portaltek.pagw.common.env.AppProfile.*;

@Configuration
class ProfileServiceConfig {

   @Profile({DEV, QA, STG, PROD})
   @Bean
   UserDetailsService userDetailsService(JwtUserFactory jwtUserFactory) {
      return new ProfileServiceAdapter(jwtUserFactory);
   }

   @Profile(LOCAL)
   @Bean
   UserDetailsService localUserDetailsService(JwtUserFactory jwtUserFactory) {
      return new LocalProfileServiceAdapter(jwtUserFactory);
   }

}
