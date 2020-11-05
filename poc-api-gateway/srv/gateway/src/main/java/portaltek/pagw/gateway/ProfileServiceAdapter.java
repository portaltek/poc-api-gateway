package portaltek.pagw.gateway;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import portaltek.pagw.common.web.security.jwt.JwtUserService;


class ProfileServiceAdapter implements UserDetailsService {

   private static final String NOT_FOUND = "No user found with username '%s'.";

   private UserDetailsService userService;

   public ProfileServiceAdapter() {
      this.userService = new JwtUserService();
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return userService.loadUserByUsername(username);
   }


}
