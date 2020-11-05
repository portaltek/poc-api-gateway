package portaltek.pagw.common.web.test;


import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import portaltek.pagw.common.web.security.jwt.JwtRequest;
import portaltek.pagw.common.web.security.jwt.JwtResponse;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static portaltek.pagw.common.web.security.jwt.JwtRequest.getEntity;
import static portaltek.pagw.common.web.security.jwt.JwtUtil.BEARER;


public class Api {

   final private Rest rest;
   final private String tokenEndpoint;

   public Api(Rest rest, String tokenEndpoint) {
      this.rest = rest;
      this.tokenEndpoint = tokenEndpoint;
   }

   public String createToken(String username, String password) {

      HttpEntity<JwtRequest> req = getEntity(username, password);
      var url = url(tokenEndpoint);
      return ofNullable(post(url, req, JwtResponse.class))
         .map(HttpEntity::getBody)
         .map(JwtResponse::getToken)
         .orElse("");
   }

   public HttpHeaders createHeaderWithNewToken(String username, String password) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.add(AUTHORIZATION, BEARER + createToken(username, password));
      return headers;
   }

   public TestRestTemplate template() {
      return rest.template();
   }

   public <T> ResponseEntity<T> get(String url, Class<T> reqType) {
      return template().getForEntity(url, reqType);
   }

   public <T> ResponseEntity<T> post(String url, Object req, Class<T> reqType) {
      return template().postForEntity(url, req, reqType);
   }

   public String url(String endpoint) {
      return rest.url(endpoint);
   }

}




