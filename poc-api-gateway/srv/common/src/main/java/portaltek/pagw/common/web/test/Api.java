package portaltek.pagw.common.web.test;


import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import portaltek.pagw.common.web.security.Credentials;
import portaltek.pagw.common.web.security.jwt.JwtRequest;
import portaltek.pagw.common.web.security.jwt.JwtResponse;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.GET;
import static portaltek.pagw.common.web.security.jwt.JwtRequest.getEntity;


public class Api {

   final private Rest rest;
   final private String createTokenPath;

   public Api(Rest rest, String createTokenPath) {
      this.rest = rest;
      this.createTokenPath = createTokenPath;
   }

   public String createToken(Credentials credentials) {

      HttpEntity<JwtRequest> req = getEntity(credentials);
      return ofNullable(post(createTokenPath, req, JwtResponse.class))
         .map(HttpEntity::getBody)
         .map(JwtResponse::getToken)
         .orElse("");
   }

   public HttpHeaders createHeader(Credentials credentials) {
      HttpHeaders headers = createEmptyJsonHeader();
      headers.setBearerAuth(createToken(credentials));
      return headers;
   }

   public HttpEntity<?> createReq(Credentials credentials, Object body) {
      HttpHeaders headers = createHeader(credentials);
      return new HttpEntity<>(body, headers);
   }

   public HttpEntity<?> createReq(Object body) {
      HttpHeaders headers = createEmptyJsonHeader();
      return new HttpEntity<>(body, headers);
   }

   public HttpHeaders createEmptyJsonHeader() {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      return headers;
   }



   public TestRestTemplate template() {
      return rest.template();
   }

   public <T> ResponseEntity<T> get(String path, Class<T> reqType) {
      return template().getForEntity(url(path), reqType);
   }

   public <T> ResponseEntity<T> get(String path, HttpEntity<?> req, Class<T> reqType) {
      return template().exchange(url(path), GET, req, reqType);
   }

   public <T> ResponseEntity<T> post(String path, Object req, Class<T> reqType) {
      return template().postForEntity(url(path), req, reqType);
   }

   public String url(String path) {
      return rest.url(path);
   }

}




