package portaltek.pagw.common.web.security;


import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import portaltek.pagw.common.web.security.jwt.*;


public abstract class GenericJwtWebSecurityConfig {

   abstract public JwtProps jwtProps();

   abstract public UserDetailsService userDetailsService(JwtUserFactory jwtUserFactory);

   @Bean
   public WebSecurityEntryPoint unauthorizedHandler() {
      return new WebSecurityEntryPoint();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public JwtUserFactory jwtUserFactory(PasswordEncoder encoder) {
      return new JwtUserFactory(encoder);
   }

   @Bean
   public JwtGenerator jwtGenerator(JwtProps props,
                                    UserDetailsService userService) {
      return new JwtGeneratorImpl(props, userService);
   }

   @Bean
   public JwtUtil jwtUtil(JwtProps jwtProps) {
      return new JwtUtil(jwtProps);
   }

   @Bean
   public JwtValidator jwtValidator(JwtUtil jwtUtil) {
      return new JwtValidator(jwtUtil);
   }

   @Bean
   public JwtFilter jwtFilter(JwtValidator jwtValidator) {
      return new JwtFilter(jwtValidator);
   }

}
