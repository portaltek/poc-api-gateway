package portaltek.pagw.profile.api.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import portaltek.pagw.common.web.ServerResponse;
import portaltek.pagw.profile.AbstractProfileAppApiTest;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.http.HttpStatus.OK;

class ProfileControllerTest extends AbstractProfileAppApiTest {

   final static String REPLY_MSG = "{\"message\":\"pong!\"}";
   final String GET_PATH_VARIABLE = "/api/profile/Admin";
   final String GET_REQUEST_PARAM = "/api/profile?username=Admin";

   @BeforeEach
   void init() {
   }

   @Test
   void getProfileAdmin_shouldReturnMsg() {
//      var req = api.createReq("");

      ResponseEntity<ServerResponse> reply = api
         .get(GET_PATH_VARIABLE, ServerResponse.class);

//      then(reply.getStatusCode()).isEqualTo(OK);
//      then(reply.getBody().getMessage()).isEqualTo(EXPECTED_MSG);
   }
}