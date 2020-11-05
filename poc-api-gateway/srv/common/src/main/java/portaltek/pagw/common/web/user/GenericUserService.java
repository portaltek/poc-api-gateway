package portaltek.pagw.common.web.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static portaltek.pagw.common.web.user.GenericUserFactory.create;


public class GenericUserService implements UserDetailsService {

   static final String NOT_FOUND = "No user found with username '%s'.";

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return ofNullable(create(username, Set.of("ADMIN", "USER")))
         .orElseThrow(() -> usernameNotFound(username));
   }

   public UsernameNotFoundException usernameNotFound(String username) {
      return new UsernameNotFoundException(format(NOT_FOUND, username));
   }


}
