package portaltek.pagw.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = false)
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponse {

   private String message;

   public static ServerResponse of(String message) {
      return new ServerResponse(message);
   }
}
