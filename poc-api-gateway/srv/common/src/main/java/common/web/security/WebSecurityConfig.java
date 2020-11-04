package common.web.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public abstract class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final Log log = LogFactory.getLog(this.getClass());
   public static String[] ANONYMOUS_RESOURCES = {"/", "/*.html",
      "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.jsp"};


   @Autowired
   private WebSecurityEntryPoint unauthorizedHandler;

   @Autowired
   private UserDetailsService userDetailsService;

   @Autowired
   public void configureAuthentication(AuthenticationManagerBuilder builder) throws Exception {
      builder
         .userDetailsService(userDetailsService)
         .passwordEncoder(passwordEncoder());
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }

   @Bean
   public TokenFilter tokenFilter() {
      return new TokenFilter();
   }

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {

      httpSecurity.authorizeRequests()
         .antMatchers(HttpMethod.GET, ANONYMOUS_RESOURCES).permitAll()
         .antMatchers(HttpMethod.OPTIONS).permitAll()
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
         tokenFilter(),
         UsernamePasswordAuthenticationFilter.class
      );

      httpSecurity.csrf().disable();
      httpSecurity.headers().cacheControl().disable();
      httpSecurity.headers().frameOptions().disable();

   }


}