package portaltek.pagw.common.web.security.jwt;

import lombok.Value;

@Value
public class JwtProps {

   String tokenHeader;
   String refreshTokenHeader;
   String secret;
   Long expiration;

   public JwtProps(String tokenHeader,
                   String refreshTokenHeader,
                   String secret,
                   Long expiration) {
      this.tokenHeader = tokenHeader;
      this.refreshTokenHeader = refreshTokenHeader;
      this.secret = secret;
      this.expiration = expiration;
   }


}
