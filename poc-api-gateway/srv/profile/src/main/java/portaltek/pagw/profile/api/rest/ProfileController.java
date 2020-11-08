package portaltek.pagw.profile.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import portaltek.pagw.common.web.ServerResponse;

@RestController
class ProfileController {

   @GetMapping(value = "/api/profile/ping")
   ResponseEntity<ServerResponse> profilePing() {

      String msg = "pong!";
      return ResponseEntity.ok(new ServerResponse(msg));
   }

   @GetMapping(value = "/api/profile/{username}")
   ResponseEntity<ServerResponse> profileByPathVariable(@PathVariable String username) {

      String msg = "profileByPathVariable: " + username;
      return ResponseEntity.ok(new ServerResponse(msg));
   }

   @GetMapping(value = "/api/profile")
   ResponseEntity<ServerResponse> profileByRequestParam(@RequestParam String username) {

      String msg = "profileByRequestParam: " + username;
      return ResponseEntity.ok(new ServerResponse(msg));
   }




}
