package portaltek.pagw.common.web.test;


import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpMethod.GET;


public class Api {

   final private Rest rest;

   public Api(Rest rest) {
      this.rest = rest;
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




