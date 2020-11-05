package portaltek.pagw.common.web.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUserFactory {
   private JwtUserFactory() {
   }

   public static JwtUser create(String username, Set<String> authList) {
      return new JwtUser(
         1L, username, username, map(authList), true
      );
   }

   public static List<GrantedAuthority> map(Set<String> authorities) {
      return authorities.stream()
         .map(JwtUserFactory::createAuth)
         .collect(Collectors.toList());
   }


   public static SimpleGrantedAuthority createAuth(String authority) {
      return new SimpleGrantedAuthority("ROLE_"
         + authority.toUpperCase());
   }

}
