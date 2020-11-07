package portaltek.pagw.gateway.api.rest.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import portaltek.pagw.common.web.security.WebSecurityEntryPoint;
import portaltek.pagw.common.web.security.jwt.*;
import portaltek.pagw.gateway.spi.profile.LocalProfileServiceAdapter;
import portaltek.pagw.gateway.spi.profile.ProfileServiceAdapter;

import static portaltek.pagw.common.env.AppProfile.*;


@Configuration
class JwtWebSecurityConfig {

   @Value("${jwt.header}")
   private String tokenHeader;
   @Value("${jwt.refresh.header}")
   private String refreshTokenHeader;
   @Value("${jwt.secret}")
   private String secret;
   @Value("${jwt.expiration}")
   private Long expiration;

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public JwtUserFactory jwtUserFactory(PasswordEncoder encoder) {
      return new JwtUserFactory(encoder);
   }

   @Profile({DEV, QA, STG, PROD})
   @Bean
   public UserDetailsService userDetailsService(JwtUserFactory jwtUserFactory) {
      return new ProfileServiceAdapter(jwtUserFactory);
   }

   @Profile(LOCAL)
   @Bean
   public UserDetailsService localUserDetailsService(JwtUserFactory jwtUserFactory) {
      return new LocalProfileServiceAdapter(jwtUserFactory);
   }
   @Bean
   public WebSecurityEntryPoint unauthorizedHandler() {
      return new WebSecurityEntryPoint();
   }

   @Bean
   public JwtUtil jwtUtil() {
      return new JwtUtil(secret);
   }

   @Bean
   public JwtValidator tokenValidator(JwtUtil jwtUtil) {
      return new JwtValidator(jwtUtil, tokenHeader, refreshTokenHeader);
   }

   @Bean
   public JwtFilter tokenFilter(JwtValidator jwtValidator) {
      return new JwtFilter(jwtValidator);
   }


   @Bean
   public JwtGenerator jwtGenerator(UserDetailsService profileServiceAdapter) {
      return new JwtGeneratorImpl(secret, expiration, profileServiceAdapter);
   }


}
