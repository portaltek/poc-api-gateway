package portaltek.pagw.gateway;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import portaltek.pagw.common.web.security.GenericWebSecurityConfig;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(JwtWebSecurityConfig.class)
class GatewayWebSecurityConfigAdapter extends GenericWebSecurityConfig {


}