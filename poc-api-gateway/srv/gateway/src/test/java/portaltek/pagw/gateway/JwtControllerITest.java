package portaltek.pagw.gateway;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import portaltek.pagw.common.env.AppProfile;
import portaltek.pagw.common.web.test.Api;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApp.class, webEnvironment = RANDOM_PORT)
@Import(GatewayAppTestConfig.class)
@ActiveProfiles(AppProfile.LOCAL)
class JwtControllerITest {

   final static String EXPECTED_MSG = "{\"message\":\"hi!\"}";

   @Autowired
   Api api;
   String pingUrl;
   String createUrl;

   @BeforeEach
   public void init() {
      pingUrl = api.url("/api/open/token/hi");
      createUrl = api.url("/api/open/token/create");
   }

   @Test
   public void postValidLogin_shouldReturnToken() {
      String token = api.createToken("admin", "admin");
      then(token).isNotNull();
   }

   @Test
   public void postInvalidLogin_shouldReturnEmpty() {
      String token = api.createToken("admin", "admin2");
      then(token).isEmpty();
   }

   @Test
   public void getPing_shouldReturnPong() {

      var response = api.get(pingUrl, String.class);
      then(response.getStatusCode()).isEqualTo(OK);
      then(response.getBody()).isEqualTo(EXPECTED_MSG);
   }


}



