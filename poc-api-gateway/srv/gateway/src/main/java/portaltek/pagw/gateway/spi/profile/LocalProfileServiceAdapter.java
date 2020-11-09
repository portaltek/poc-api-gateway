package portaltek.pagw.gateway.spi.profile;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import portaltek.pagw.common.web.security.jwt.JwtUserFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Set.of;


class LocalProfileServiceAdapter implements UserDetailsService {

   private final Map<String, UserDetails> userMap = new ConcurrentHashMap<>();
   private final JwtUserFactory factory;

   public LocalProfileServiceAdapter(JwtUserFactory factory) {
      this.factory = factory;
      userMap.put("admin", factory.create(1L, "admin", of("ADMIN", "USER")));
      userMap.put("user", factory.create(2L, "user", of("USER")));
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return Optional.ofNullable(userMap.get(username))
         .orElseThrow(() -> new UsernameNotFoundException(""));
   }


}
