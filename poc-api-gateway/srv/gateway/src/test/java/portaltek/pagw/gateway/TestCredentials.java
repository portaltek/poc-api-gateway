package portaltek.pagw.gateway;

import portaltek.pagw.common.web.security.Credentials;

public class TestCredentials {
   public static final Credentials ADMIN = new Credentials("admin", "admin");
   public static final Credentials ADMIN_INVALID = new Credentials("admin", "admin2");
   public static final Credentials USER = new Credentials("user", "user");
}
