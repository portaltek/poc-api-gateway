package portaltek.pagw.common.web;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class ServerResponse {

   private String message;

   public ServerResponse(String message) {
      super();
      this.message = message;
   }

   public ServerResponse() {
      super();
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public static ResponseEntity<?> of(AuthenticationException e) {
      return ResponseEntity
         .status(UNAUTHORIZED)
         .body(new ServerResponse(e.getMessage()));
   }

   public static ServerResponse of(String message) {
      return new ServerResponse(message);
   }
}
