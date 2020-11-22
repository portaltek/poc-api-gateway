package portaltek.pagw.common.web.security.jwt;


import io.jsonwebtoken.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class JwtUtil implements Serializable {

   private static final long serialVersionUID = -3301605591108950417L;
   public static final String CLAIM_KEY_ID = "jti";
   public static final String CLAIM_KEY_USERID = "userId";
   public static final String CLAIM_KEY_USERNAME = "sub";
   public static final String CLAIM_KEY_CREATED = "created";
   public static final String CLAIM_KEY_ROLES = "roles";
   public static final String BEARER = "Bearer ";

   final private JwtProps props;

   public JwtUtil(JwtProps props) {
      this.props = props;
   }

   public JwtProps getProps() {
      return props;
   }

   private Claims getClaimsFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException,
      MalformedJwtException, IllegalArgumentException {

      return Jwts.parser()
         .setSigningKey(props.secret())
         .parseClaimsJws(removePrefix(token))
         .getBody();

   }

   public String getIdFromToken(String token) {
      try {
         final Claims claims = getClaimsFromToken(token);
         return claims.getId();
      } catch (Exception e) {
         return null;
      }
   }

   public Long getUserIdFromToken(String token) {
      try {
         final Claims claims = getClaimsFromToken(token);
         return Long.parseLong(claims.get(CLAIM_KEY_USERID).toString());
      } catch (Exception e) {
         return null;
      }
   }

   public String getUsernameFromToken(String token) {
      try {
         final Claims claims = getClaimsFromToken(token);
         return claims.getSubject();
      } catch (Exception e) {
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
      List<String> roles;
      try {
         final Claims claims = getClaimsFromToken(token);
         roles = (List<String>) claims.get(CLAIM_KEY_ROLES);
      } catch (Exception e) {
         roles = null;
      }
      return roles != null ? roles.stream()
         .map(SimpleGrantedAuthority::new)
         .collect(Collectors.toList()) : null;
   }

   public Date getCreatedDateFromToken(String token) {
      try {
         final Claims claims = getClaimsFromToken(token);
         return new Date((Long) claims.get(CLAIM_KEY_CREATED));
      } catch (Exception e) {
         return null;
      }
   }

   public Date getExpirationDateFromToken(String token) {
      try {
         final Claims claims = getClaimsFromToken(token);
         return claims.getExpiration();
      } catch (Exception e) {
         return null;
      }
   }

   public Boolean isTokenExpired(String token) {
      try {
         getClaimsFromToken(token);
         return false;
      } catch (ExpiredJwtException ex) {
         return true;
      }

   }

   public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
      return (lastPasswordReset != null && created.before(lastPasswordReset));
   }


   public Boolean isValid(String token) {
      try {
         getClaimsFromToken(token);
         return true;
      } catch (Exception ex) {
         return false;
      }
   }

   public boolean hasToken(String header) {
      return header != null && header.startsWith(BEARER);
   }

   public String removePrefix(String header) {
      return header.replace(BEARER, "");
   }


}
