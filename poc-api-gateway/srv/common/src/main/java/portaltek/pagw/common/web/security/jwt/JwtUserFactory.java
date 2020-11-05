package portaltek.pagw.common.web.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUserFactory {

   private PasswordEncoder encoder;

   public JwtUserFactory(PasswordEncoder encoder) {
      this.encoder = encoder;
   }

   public JwtUser create(Long id, String username, Set<String> authList) {
      return new JwtUser(
         id, username, hash(username), map(authList), true
      );
   }

   public String hash(String string) {
      return encoder.encode(string);
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
