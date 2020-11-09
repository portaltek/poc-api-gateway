package portaltek.pagw.common.web;

import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class Credentials {
   String username;
   String password;
}
