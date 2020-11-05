package portaltek.pagw.gateway;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import portaltek.pagw.common.web.security.jwt.JwtFilter;
import portaltek.pagw.common.web.security.jwt.JwtValidator;
import portaltek.pagw.common.web.security.WebSecurityEntryPoint;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({JwtWebSecurityConfig.class})
class GatewayWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

   private final Log log = LogFactory.getLog(this.getClass());
   public static String[] ANONYMOUS_RESOURCES = {"/", "/*.html",
      "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.jsp"};

   private WebSecurityEntryPoint unauthorizedHandler;
   private ProfileServiceAdapter profileServiceAdapter;
   private PasswordEncoder passwordEncoder;
   private JwtFilter jwtFilter;

   @Autowired
   public GatewayWebSecurityConfigAdapter(WebSecurityEntryPoint unauthorizedHandler,
                                          ProfileServiceAdapter profileServiceAdapter,
                                          PasswordEncoder passwordEncoder,
                                          JwtFilter jwtFilter) {
      this.unauthorizedHandler = unauthorizedHandler;
      this.profileServiceAdapter = profileServiceAdapter;
      this.passwordEncoder = passwordEncoder;
      this.jwtFilter = jwtFilter;
   }

   @Autowired
   public void configureAuthentication(AuthenticationManagerBuilder builder) throws Exception {
      builder
         .userDetailsService(profileServiceAdapter)
         .passwordEncoder(passwordEncoder);
   }


   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {

      configureAuthorizeRequests(httpSecurity)
         .exceptionHandling()
         .authenticationEntryPoint(unauthorizedHandler)

         .and()
         .sessionManagement()
         .sessionCreationPolicy(STATELESS);

      httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
      httpSecurity.csrf().disable();
      httpSecurity.headers().cacheControl().disable();
      httpSecurity.headers().frameOptions().disable();

   }

   protected HttpSecurity configureAuthorizeRequests(HttpSecurity httpSecurity) throws Exception {
      return httpSecurity.authorizeRequests()
         .antMatchers(GET, ANONYMOUS_RESOURCES).permitAll()
         .antMatchers(OPTIONS).permitAll()
         .antMatchers("/api/open/**").permitAll()
         .antMatchers("/console/**").permitAll()
         .antMatchers("/actuator/health").permitAll()
         .anyRequest().authenticated().and();
   }


}