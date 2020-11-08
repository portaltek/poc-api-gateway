package portaltek.pagw.profile;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@RestController
public class ProfileApp {


   public static void main(String[] args) {
      run(ProfileApp.class, args);
   }
}

