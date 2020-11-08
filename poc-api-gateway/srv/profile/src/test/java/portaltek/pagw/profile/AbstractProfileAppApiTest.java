package portaltek.pagw.profile;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import portaltek.pagw.common.env.AppProfile;
import portaltek.pagw.common.web.test.Api;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProfileApp.class, webEnvironment = RANDOM_PORT)
@Import(ProfileAppApiTestConfig.class)
@ActiveProfiles(AppProfile.LOCAL)
public abstract class AbstractProfileAppApiTest {
   @Autowired
   protected Api api;
}