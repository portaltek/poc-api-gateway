package portaltek.pagw.gateway;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import portaltek.pagw.common.env.AppProfile;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApp.class, webEnvironment = RANDOM_PORT)
@Import(GatewayAppTestConfig.class)
@ActiveProfiles(AppProfile.LOCAL)
class GatewayAppTest {

   @Autowired
   ApplicationContext ctx;

   @Test
   public void contextLoads() {
      assertNotNull(ctx);
   }


}