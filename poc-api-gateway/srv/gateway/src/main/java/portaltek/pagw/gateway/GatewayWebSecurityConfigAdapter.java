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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import portaltek.pagw.common.web.security.TokenFilter;
import portaltek.pagw.common.web.security.TokenValidator;
import portaltek.pagw.common.web.security.WebSecurityEntryPoint;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({GatewayWebSecurityConfig.class})
class GatewayWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

   private final Log log = LogFactory.getLog(this.getClass());
   public static String[] ANONYMOUS_RESOURCES = {"/", "/*.html",
      "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.jsp"};

   private WebSecurityEntryPoint unauthorizedHandler;
   private ProfileServiceAdapter profileServiceAdapter;
   private PasswordEncoder passwordEncoder;
   private TokenValidator tokenValidator;

   @Autowired
   public GatewayWebSecurityConfigAdapter(WebSecurityEntryPoint unauthorizedHandler,
                                          ProfileServiceAdapter profileServiceAdapter,
                                          PasswordEncoder passwordEncoder,
                                          TokenValidator tokenValidator) {
      this.unauthorizedHandler = unauthorizedHandler;
      this.profileServiceAdapter = profileServiceAdapter;
      this.passwordEncoder = passwordEncoder;
      this.tokenValidator = tokenValidator;
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

   @Bean
   public TokenFilter authenticationTokenFilterBean() {
      return new TokenFilter(tokenValidator);
   }


   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {

      httpSecurity.authorizeRequests()
         .antMatchers(GET, ANONYMOUS_RESOURCES).permitAll()
         .antMatchers(OPTIONS).permitAll()
         .antMatchers("/api/open/**").permitAll()
         .antMatchers("/console/**").permitAll()
         .antMatchers("/actuator/health").permitAll()
         .anyRequest().authenticated()

         .and()
         .exceptionHandling()
         .authenticationEntryPoint(unauthorizedHandler)

         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

         .and();

      httpSecurity.addFilterBefore(
         authenticationTokenFilterBean(),
         UsernamePasswordAuthenticationFilter.class
      );

      httpSecurity.csrf().disable();
      httpSecurity.headers().cacheControl().disable();
      httpSecurity.headers().frameOptions().disable();

   }


}