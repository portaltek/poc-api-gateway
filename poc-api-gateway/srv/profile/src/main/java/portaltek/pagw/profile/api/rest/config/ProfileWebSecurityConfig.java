package portaltek.pagw.profile.api.rest.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
class ProfileWebSecurityConfig extends GenericWebSecurityConfig {

   @Autowired
   public ProfileWebSecurityConfig(UserDetailsService userDetailService,
                                   PasswordEncoder passwordEncoder,
                                   WebSecurityEntryPoint unauthorizedHandler,
                                   JwtFilter jwtFilter) {
      super(userDetailService, passwordEncoder, unauthorizedHandler, jwtFilter);
   }
}