package portaltek.pagw.gateway;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import portaltek.pagw.common.env.AppProfile;
import portaltek.pagw.common.web.security.jwt.JwtApi;
import portaltek.pagw.common.web.test.Api;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApp.class, webEnvironment = RANDOM_PORT)
@Import(GatewayAppComponentTestConfig.class)
@ActiveProfiles(AppProfile.LOCAL)
public abstract class AbstractGatewayAppComponentTest {
   @Autowired
   protected Api api;
   @Autowired
   protected JwtApi jwt;

}