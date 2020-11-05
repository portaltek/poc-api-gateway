package portaltek.pagw.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import portaltek.pagw.common.web.security.AbstractWebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import portaltek.pagw.common.web.security.WebSecurityEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class GatewayWebSecurityConfig extends AbstractWebSecurityConfig {

   @Autowired
   protected WebSecurityEntryPoint unauthorizedHandler;


   @Override
   protected WebSecurityEntryPoint unauthorizedHandler() {
      return unauthorizedHandler;
   }

   @Override
   protected UserDetailsService userDetailsService() {
      return null;
   }
}
