package portaltek.pagw.gateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@RestController
public class GatewayApp {
   public static void main(String[] args) {
      run(GatewayApp.class, args);
   }
}

