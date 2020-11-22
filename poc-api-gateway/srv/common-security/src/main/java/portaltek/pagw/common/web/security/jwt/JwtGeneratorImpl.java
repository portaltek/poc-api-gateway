package portaltek.pagw.common.web.security.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.lang.System.currentTimeMillis;
import static org.springframework.security.core.authority.AuthorityUtils.authorityListToSet;
import static portaltek.pagw.common.web.security.jwt.JwtUtil.*;


public class JwtGeneratorImpl implements JwtGenerator {

   private static final long serialVersionUID = -3301605591108950416L;

   private JwtProps props;
   private UserDetailsService userDetailsService;
   final private SignatureAlgorithm signatureAlgorithm = HS512;

   public JwtGeneratorImpl(JwtProps props,
                           UserDetailsService userDetailsService) {
      this.props = props;
      this.userDetailsService = userDetailsService;
   }

   @Override
   public JwtResponse create(String username) {

      Map<String, Object> claims = createClaims(username);
      String token = createToken(claims);
      String refreshToken = createRefreshToken(claims);
      return JwtResponse.of(token, refreshToken);
   }


   @Override
   public JwtResponse refresh(String username) {
      try {
         final Map<String, Object> claims = createClaims(username);
         String newToken = createToken(claims);
         String newRefreshToken = createRefreshToken(claims);
         return JwtResponse.of(newToken, newRefreshToken);
      } catch (Exception e) {
         return JwtResponse.of(null, null);
      }
   }

   private Map<String, Object> createClaims(String username) {
      final JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);
      Map<String, Object> claims = new HashMap<>();

      claims.put(CLAIM_KEY_ID, UUID.randomUUID().toString());
      claims.put(CLAIM_KEY_USERID, userDetails.getId());
      claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
      claims.put(CLAIM_KEY_CREATED, new Date());
      claims.put(CLAIM_KEY_ROLES, authorityListToSet(userDetails.getAuthorities()));

      return claims;
   }

   private String createToken(Map<String, Object> claims) {
      return Jwts.builder()
         .setClaims(claims)
         .setExpiration(generateExpirationDate("token"))
         .signWith(signatureAlgorithm, props.secret())
         .compact();
   }

   private String createRefreshToken(Map<String, Object> claims) {
      return Jwts.builder()
         .setClaims(claims)
         .setExpiration(generateExpirationDate("refresh"))
         .signWith(signatureAlgorithm, props.secret())
         .compact();
   }

   private Date generateExpirationDate(String type) {
      if (type.equals("token")) {
         return new Date(currentTimeMillis()
            + props.expiration() * 1000);
      } else {
         return new Date(currentTimeMillis()
            + props.expiration() * 5 * 1000);
      }
   }

}
