package filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CorsResponseFilter implements ContainerResponseFilter {

   @Override
   public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
      responseContext.getHeaders().add(
               "Access-Control-Allow-Origin", "*");
      responseContext.getHeaders().add(
               "Access-Control-Allow-Credentials", "true");
      responseContext.getHeaders().add(
               "Access-Control-Allow-Headers",
               "origin, content-type, accept, authorization, X-Requested-With, x-access-token");
      responseContext.getHeaders().add(
               "Access-Control-Allow-Methods",
               "GET, POST, PUT, DELETE, OPTIONS, HEAD");
   }
}
