package config;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
@DeclareRoles({
   "ADMIN", "CUSTOMER", "VIP"
})
public class RentApp extends Application {
   public RentApp() {
   }

}
