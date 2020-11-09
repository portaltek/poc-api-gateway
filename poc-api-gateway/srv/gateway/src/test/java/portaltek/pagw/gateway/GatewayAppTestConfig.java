package portaltek.pagw.gateway;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import portaltek.pagw.common.web.security.jwt.JwtApi;
import portaltek.pagw.common.web.test.Api;
import portaltek.pagw.common.web.test.Rest;

@Lazy
@TestConfiguration
public class GatewayAppTestConfig {
   private final String host = "http://localhost:";
   private final String createTokenPath = "/api/open/token/create";
   @LocalServerPort
   private int port;

   @Bean
   public Rest rest(TestRestTemplate template) {
      return new Rest(template, host, port);
   }

   @Bean
   public Api api(Rest rest) {
      return new Api(rest);
   }

   @Bean
   public JwtApi jwtApi(Api api) {
      return new JwtApi(api, createTokenPath);
   }
}



