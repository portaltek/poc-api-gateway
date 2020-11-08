package portaltek.pagw.gateway.api.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import portaltek.pagw.common.web.ServerResponse;


@RestController
class GreetingController {


   @GetMapping(value = "/api/auth/admin")
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   ResponseEntity<ServerResponse> greetingAdmin() {

      String msg = "Hello Authenticated Admin";
      return ResponseEntity.ok(new ServerResponse(msg));
   }

   @GetMapping(value = "/api/auth/user")
   ResponseEntity<ServerResponse> greetingUser() {

      String msg = "Hello Authenticated User";
      return ResponseEntity.ok(new ServerResponse(msg));
   }
}