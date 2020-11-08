package portaltek.pagw.gateway.api.rest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import portaltek.pagw.common.env.AppProfile;
import portaltek.pagw.common.web.test.Api;
import portaltek.pagw.gateway.GatewayApp;
import portaltek.pagw.gateway.GatewayAppTestConfig;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static portaltek.pagw.gateway.TestCredentials.ADMIN;
import static portaltek.pagw.gateway.TestCredentials.ADMIN_INVALID;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApp.class, webEnvironment = RANDOM_PORT)
@Import(GatewayAppTestConfig.class)
@ActiveProfiles(AppProfile.LOCAL)
class JwtControllerITest {

   final static String EXPECTED_MSG = "{\"message\":\"hi!\"}";

   @Autowired
   Api api;
   final String POST_HI = "/api/auth/token/hi";
   final String GET_HI = "/api/open/token/hi";


   @BeforeEach
   void init() {
   }

   @Test
   void postValidLogin_shouldReturnToken() {
      String token = api.createToken(ADMIN);
      then(token).isNotNull();
   }

   @Test
   void postInvalidLogin_shouldReturnEmpty() {
      String token = api.createToken(ADMIN_INVALID);
      then(token).isEmpty();
   }

   @Test
   void getPing_shouldReturnHi() {
      ResponseEntity<String> response = api.get(GET_HI, String.class);

      then(response.getStatusCode()).isEqualTo(OK);
      then(response.getBody()).isEqualTo(EXPECTED_MSG);
   }


   @Test
   void givenValidAdmin_postHi_shouldReturnHi() {
      var req = api.createReq(ADMIN, "");

      ResponseEntity<String> response = api.post(POST_HI, req, String.class);

      then(response.getStatusCode()).isEqualTo(OK);
      then(response.getBody()).isEqualTo(EXPECTED_MSG);
   }


   @Test
   void givenInValidToken_postHi_shouldReturn401() {
      var header = api.createEmptyJsonHeader();
      header.setBearerAuth("123456");
      var req = new HttpEntity<>("", header);

      ResponseEntity<String> response = api.post(POST_HI, req, String.class);

      then(response.getStatusCode()).isEqualTo(UNAUTHORIZED);
   }
}



