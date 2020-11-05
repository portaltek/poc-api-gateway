package portaltek.pagw.gateway;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import portaltek.pagw.common.web.user.GenericUserService;


class ProfileServiceAdapter implements UserDetailsService {

   static final String NOT_FOUND = "No user found with username '%s'.";

   private UserDetailsService userService;

   public ProfileServiceAdapter() {
      this.userService = new GenericUserService();
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return userService.loadUserByUsername(username);
   }


}
