package common.web.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
class RestConfig {
   private final Log log = LogFactory.getLog(this.getClass());

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.addAllowedOrigin("http://localhost:4200");
      config.addAllowedHeader("*");
      config.addAllowedMethod("OPTIONS");
      config.addAllowedMethod("GET");
      config.addAllowedMethod("POST");
      config.addAllowedMethod("PUT");
      config.addAllowedMethod("DELETE");
      source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
   }

}
