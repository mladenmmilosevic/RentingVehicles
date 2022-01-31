package config;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/*import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
Problem sa JWT ne radi Swagger presece ga filter
Zamena Postman dokumentacija
@SwaggerDefinition (info = @Info (
        title = "Rent-Service",
        description = "A simple example of apiee",
        version = "1.0.0",
        contact = @Contact (
            name = "Mladen",
            email = "apiee@phillip-kruger.com",
            url = "http://www.energosoft.rs/"
        )
    )
)
 */
@ApplicationPath("api")
@DeclareRoles({ "admin","customer","vip" })

public class RentApp extends Application {
   public RentApp() {}

}
