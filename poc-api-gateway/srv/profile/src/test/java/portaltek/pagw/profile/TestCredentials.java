package portaltek.pagw.profile;

import portaltek.pagw.common.web.Credentials;

public class TestCredentials {
   public static final Credentials ADMIN = new Credentials("admin", "admin");
   public static final Credentials ADMIN_INVALID = new Credentials("admin", "admin2");
   public static final Credentials USER = new Credentials("user", "user");
}
