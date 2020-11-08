package portaltek.pagw.common.web.jwt;

import java.io.Serializable;

public interface JwtGenerator extends Serializable {
   JwtResponse create(String username);

   JwtResponse refresh(String username);
}
