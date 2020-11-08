package portaltek.pagw.gateway.api.rest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import portaltek.pagw.common.env.AppProfile;
import portaltek.pagw.common.web.security.ServerResponse;
import portaltek.pagw.common.web.test.Api;
import portaltek.pagw.gateway.GatewayApp;
import portaltek.pagw.gateway.GatewayAppTestConfig;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
import static portaltek.pagw.gateway.TestCredentials.ADMIN;
import static portaltek.pagw.gateway.TestCredentials.USER;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApp.class, webEnvironment = RANDOM_PORT)
@Import(GatewayAppTestConfig.class)
@ActiveProfiles(AppProfile.LOCAL)
class GreetingControllerITest {

   final static String EXPECTED_MSG = "{\"message\":\"hi!\"}";

   @Autowired
   Api api;
   final String GET_ADMIN = "/api/auth/admin";
   final String GET_USER = "/api/auth/user";
   final String MSG_ADMIN = "Hello Authenticated Admin";
   final String MSG_USER = "Hello Authenticated User";

   @BeforeEach
   void init() {
   }

   @Test
   void givenAdminCredentials_getAdmin_shouldReturnMsg() {
      var req = api.createReq(ADMIN, "");

      ResponseEntity<ServerResponse> reply = api.get(GET_ADMIN, req, ServerResponse.class);

      then(reply.getStatusCode()).isEqualTo(OK);
      then(reply.getBody().getMessage()).isEqualTo(MSG_ADMIN);
   }

   @Test
   void givenUserCredentials_getAdmin_shouldReturn403() {
      var req = api.createReq(USER, "");

      ResponseEntity<ServerResponse> reply = api.get(GET_ADMIN, req, ServerResponse.class);

      then(reply.getStatusCode()).isEqualTo(FORBIDDEN);
   }

   @Test
   void givenAdminCredentials_getUser_shouldReturnMsg() {
      var req = api.createReq(ADMIN, "");

      ResponseEntity<ServerResponse> reply = api.get(GET_USER, req, ServerResponse.class);

      then(reply.getStatusCode()).isEqualTo(OK);
      then(reply.getBody().getMessage()).isEqualTo(MSG_USER);
   }

   @Test
   void givenUserCredentials_getUser_shouldReturnMsg() {
      var req = api.createReq(USER, "");

      ResponseEntity<ServerResponse> reply = api.get(GET_USER, req, ServerResponse.class);

      then(reply.getStatusCode()).isEqualTo(OK);
      then(reply.getBody().getMessage()).isEqualTo(MSG_USER);
   }

}



