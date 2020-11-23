package portaltek.pagw.common.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import portaltek.pagw.common.web.security.jwt.JwtFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


public class GenericWebSecurityConfig extends WebSecurityConfigurerAdapter {
   
   protected static String[] ANONYMOUS_RESOURCES = {"/", "/*.html",
      "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.jsp"};

   protected UserDetailsService userDetailService;
   protected PasswordEncoder passwordEncoder;
   protected WebSecurityEntryPoint unauthorizedHandler;
   protected JwtFilter jwtFilter;

   public GenericWebSecurityConfig(UserDetailsService userDetailService,
                                   PasswordEncoder passwordEncoder,
                                   WebSecurityEntryPoint unauthorizedHandler,
                                   JwtFilter jwtFilter) {
      this.userDetailService = userDetailService;
      this.passwordEncoder = passwordEncoder;
      this.unauthorizedHandler = unauthorizedHandler;
      this.jwtFilter = jwtFilter;
   }



   @Autowired
   protected void configureAuthentication(AuthenticationManagerBuilder builder) throws Exception {
      builder
         .userDetailsService(userDetailService)
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
         .antMatchers("/h2-console/**").permitAll()
         .antMatchers("/actuator/health").permitAll()
         .anyRequest().authenticated().and();
   }


}
