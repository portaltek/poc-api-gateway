package portaltek.pagw.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class GatewayAppTest extends GatewayAppIntegrationTest {

   @Autowired
   ApplicationContext ctx;

   @Test
   public void contextLoads() {
      assertNotNull(ctx);
   }


}