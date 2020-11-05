package portaltek.pagw.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import portaltek.pagw.common.web.security.jwt.JwtGenerator;
import portaltek.pagw.common.web.security.jwt.JwtRequest;
import portaltek.pagw.common.web.security.jwt.JwtResponse;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
class JwtController {

   private AuthenticationManager authManager;
   private JwtGenerator jwtGenerator;

   @Autowired
   public JwtController(AuthenticationManager authManager,
                        JwtGenerator jwtGenerator) {
      this.authManager = authManager;
      this.jwtGenerator = jwtGenerator;
   }

   @PostMapping(value = "/api/open/token/create")
   public ResponseEntity<?> createJwt(@RequestBody JwtRequest req)
      throws AuthenticationException {

      try {
         var credentials = new UsernamePasswordAuthenticationToken(
            req.getUsername(), req.getPassword());
         final var auth = authManager.authenticate(credentials);
         getContext().setAuthentication(auth);

      } catch (AuthenticationException e) {
         return JwtResponse.of(e);
      }

      var response = jwtGenerator.create(req.getUsername());
      return ok(response);
   }

}
