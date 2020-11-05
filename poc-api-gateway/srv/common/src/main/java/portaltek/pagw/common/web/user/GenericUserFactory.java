package portaltek.pagw.common.web.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenericUserFactory {
   private GenericUserFactory() {
   }

   public static GenericUser create(String username, Set<String> authList) {
      return new GenericUser(
         1L, username, username, map(authList), true
      );
   }

   public static List<GrantedAuthority> map(Set<String> authorities) {
      return authorities.stream()
         .map(GenericUserFactory::createAuth)
         .collect(Collectors.toList());
   }


   public static SimpleGrantedAuthority createAuth(String authority) {
      return new SimpleGrantedAuthority("ROLE_"
         + authority.toUpperCase());
   }

}
