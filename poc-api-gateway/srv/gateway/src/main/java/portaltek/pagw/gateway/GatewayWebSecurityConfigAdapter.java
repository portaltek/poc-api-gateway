package portaltek.pagw.gateway;


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


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(JwtWebSecurityConfig.class)
class GatewayWebSecurityConfigAdapter extends GenericWebSecurityConfig {

   @Autowired
   protected JwtFilter jwtFilter;
   @Autowired
   protected PasswordEncoder passwordEncoder;
   @Autowired
   protected UserDetailsService userDetailsService;
   @Autowired
   protected WebSecurityEntryPoint unauthorizedHandler;

   @Override
   protected WebSecurityEntryPoint unauthorizedHandler() {
      return unauthorizedHandler;
   }

   @Override
   protected UserDetailsService userDetailService() {
      return userDetailsService;
   }

   @Override
   protected PasswordEncoder passwordEncoder() {
      return passwordEncoder;
   }

   @Override
   protected JwtFilter jwtFilter() {
      return jwtFilter;
   }
}