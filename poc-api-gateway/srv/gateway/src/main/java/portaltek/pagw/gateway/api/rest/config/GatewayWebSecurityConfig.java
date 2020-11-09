package portaltek.pagw.gateway.api.rest.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import portaltek.pagw.common.web.security.GenericWebSecurityConfig;
import portaltek.pagw.common.web.security.WebSecurityEntryPoint;
import portaltek.pagw.common.web.security.jwt.JwtFilter;
import portaltek.pagw.gateway.spi.profile.JwtWebSecurityConfig;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(JwtWebSecurityConfig.class)
class GatewayWebSecurityConfig extends GenericWebSecurityConfig {

   @Autowired
   public GatewayWebSecurityConfig(UserDetailsService userDetailService,
                                   PasswordEncoder passwordEncoder,
                                   WebSecurityEntryPoint unauthorizedHandler,
                                   JwtFilter jwtFilter) {
      super(userDetailService, passwordEncoder, unauthorizedHandler, jwtFilter);
   }
}