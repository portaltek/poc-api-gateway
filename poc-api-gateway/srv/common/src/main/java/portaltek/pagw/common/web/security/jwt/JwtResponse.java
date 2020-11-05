package portaltek.pagw.common.web.security.jwt;

import org.springframework.http.ResponseEntity;

import java.io.Serializable;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class JwtResponse implements Serializable {

   private static final long serialVersionUID = 1250166508152483573L;

   private String token;
   private String refreshToken;
   private String message;

   public JwtResponse() {
   }

   public JwtResponse(String token, String refreshToken, String message) {
      this.token = token;
      this.refreshToken = refreshToken;
      this.message = message;
   }

   public String getToken() {
      return this.token;
   }

   public String getRefreshToken() {
      return refreshToken;
   }

   public String getMessage() {
      return message;
   }

   public static JwtResponse of(String token, String refreshToken) {
      return new JwtResponse(token, refreshToken, null);
   }

   public static ResponseEntity<?> of(Exception e) {
      return ResponseEntity
         .status(UNAUTHORIZED)
         .body(new JwtResponse(null, null, e.getMessage()));
   }
}
