package portaltek.pagw.common.web.test;


import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import portaltek.pagw.common.web.security.jwt.JwtRequest;
import portaltek.pagw.common.web.security.jwt.JwtResponse;
import portaltek.pagw.common.web.security.jwt.JwtUtil;

import java.util.Optional;

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

   public String token(String username, String password) {
      var url = rest.urlBase() + tokenEndpoint;
      HttpEntity<JwtRequest> req = getEntity(username, password);
      return ofNullable(rest.template().postForEntity(url, req, JwtResponse.class))
         .map(HttpEntity::getBody)
         .map(JwtResponse::getToken)
         .orElse("");
   }

   public HttpHeaders header(String username, String password) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.add(AUTHORIZATION, BEARER + token(username, password));
      return headers;
   }

   public TestRestTemplate rest() {
      return rest.template();
   }

   public String url(String endpoint) {
      return rest.url(endpoint);
   }

}




