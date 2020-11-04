package portaltek.pagw.gateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@RestController
class GatewayApp {
  @GetMapping("/api/open/hi")
  public String hi() {
    return "hi!";
  }

  public static void main(String[] args) {
    run(GatewayApp.class, args);
  }
}

