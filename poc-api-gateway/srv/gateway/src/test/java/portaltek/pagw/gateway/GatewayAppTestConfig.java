package portaltek.pagw.gateway;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import portaltek.pagw.common.web.test.Api;
import portaltek.pagw.common.web.test.Header;
import portaltek.pagw.common.web.test.Rest;

@Lazy
@TestConfiguration
class GatewayAppTestConfig {
   private final String host = "http://localhost:";
   private final String createTokenEndpoint = "/api/open/token/create";
   @LocalServerPort
   private int port;

   @Bean
   public Rest rest(TestRestTemplate template) {
      return new Rest(template, host, port);
   }

   @Bean
   public Api api(Rest rest) {
      return new Api(rest, createTokenEndpoint);
   }

   @Bean
   public Header header(Api api) {
      return new Header(api);
   }


}



