package portaltek.pagw.common.web.security.jwt;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import portaltek.pagw.common.web.Credentials;
import portaltek.pagw.common.web.test.Api;

import static java.util.Optional.ofNullable;
import static portaltek.pagw.common.web.security.jwt.JwtRequest.getEntity;


public class JwtApi {

   final private Api api;
   final private String createTokenPath;

   public JwtApi(Api api, String createTokenPath) {
      this.api = api;
      this.createTokenPath = createTokenPath;
   }

   public String createToken(Credentials credentials) {

      HttpEntity<JwtRequest> req = getEntity(credentials);
      return ofNullable(api.post(createTokenPath, req, JwtResponse.class))
         .map(HttpEntity::getBody)
         .map(JwtResponse::getToken)
         .orElse("");
   }

   public HttpHeaders createHeader(Credentials credentials) {
      HttpHeaders headers = api.createEmptyJsonHeader();
      headers.setBearerAuth(createToken(credentials));
      return headers;
   }

   public HttpEntity<?> createReq(Credentials credentials, Object body) {
      HttpHeaders headers = createHeader(credentials);
      return new HttpEntity<>(body, headers);
   }


}




