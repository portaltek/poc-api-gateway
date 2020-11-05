package portaltek.pagw.common.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import portaltek.pagw.common.web.security.jwt.JwtFilter;
import portaltek.pagw.common.web.security.jwt.JwtUtil;
import portaltek.pagw.common.web.security.jwt.JwtValidator;


public abstract class AbstractWebSecurityConfig extends WebSecurityConfigurerAdapter {

   public static String[] ANONYMOUS_RESOURCES = {"/", "/*.html",
      "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.jsp"};


   protected abstract WebSecurityEntryPoint unauthorizedHandler();

   protected abstract UserDetailsService userDetailsService();

   protected abstract JwtUtil jwtUtil();

   protected abstract JwtValidator tokenValidator(JwtUtil jwtUtil);

   protected abstract JwtFilter tokenFilter(JwtValidator jwtValidator);



   @Autowired
   protected void configureAuthentication(AuthenticationManagerBuilder builder) throws Exception {
      builder
         .userDetailsService(userDetailsService())
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
         .authenticationEntryPoint(unauthorizedHandler())

         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

         .and();

      httpSecurity.addFilterBefore(
         tokenFilter(tokenValidator(jwtUtil())),
         UsernamePasswordAuthenticationFilter.class
      );

      httpSecurity.csrf().disable();
      httpSecurity.headers().cacheControl().disable();
      httpSecurity.headers().frameOptions().disable();

   }


}