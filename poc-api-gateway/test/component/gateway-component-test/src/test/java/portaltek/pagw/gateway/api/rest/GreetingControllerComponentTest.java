package portaltek.pagw.gateway.api.rest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import portaltek.pagw.common.web.ServerResponse;
import portaltek.pagw.gateway.AbstractGatewayAppComponentTest;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
import static portaltek.pagw.gateway.TestCredentials.ADMIN;
import static portaltek.pagw.gateway.TestCredentials.USER;


class GreetingControllerComponentTest extends AbstractGatewayAppComponentTest {

   final String GET_ADMIN = "/api/auth/admin";
   final String GET_USER = "/api/auth/user";
   final String MSG_ADMIN = "Hello Authenticated Admin";
   final String MSG_USER = "Hello Authenticated User";

   @BeforeEach
   void init() {
   }

   @Test
   void givenAdminCredentials_getAdmin_shouldReturnMsg() {
      var req = jwt.createReq(ADMIN, "");

      ResponseEntity<ServerResponse> reply = api.get(GET_ADMIN, req, ServerResponse.class);

      then(reply.getStatusCode()).isEqualTo(OK);
      then(requireNonNull(reply.getBody()).getMessage()).isEqualTo(MSG_ADMIN);
   }

   @Test
   void givenUserCredentials_getAdmin_shouldReturn403() {
      var req = jwt.createReq(USER, "");

      ResponseEntity<ServerResponse> reply = api.get(GET_ADMIN, req, ServerResponse.class);

      then(reply.getStatusCode()).isEqualTo(FORBIDDEN);
   }

   @Test
   void givenAdminCredentials_getUser_shouldReturnMsg() {
      var req = jwt.createReq(ADMIN, "");

      ResponseEntity<ServerResponse> reply = api.get(GET_USER, req, ServerResponse.class);

      then(reply.getStatusCode()).isEqualTo(OK);
      then(requireNonNull(reply.getBody()).getMessage()).isEqualTo(MSG_USER);
   }

   @Test
   void givenUserCredentials_getUser_shouldReturnMsg() {
      var req = jwt.createReq(USER, "");

      ResponseEntity<ServerResponse> reply = api.get(GET_USER, req, ServerResponse.class);

      then(reply.getStatusCode()).isEqualTo(OK);
      then(requireNonNull(reply.getBody()).getMessage()).isEqualTo(MSG_USER);
   }

}



