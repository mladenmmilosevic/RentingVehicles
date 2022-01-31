
package filters;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jose4j.jwt.consumer.InvalidJwtException;

import helpentities.User;
import security.SecurityUtils;
import security.TokenSecurity;

@WebFilter(urlPatterns = "/*")
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter{


   @Context
   private ResourceInfo resourceInfo;

   @Context
   private HttpServletRequest httpRequest;

   public static final String HEADER_PROPERTY_ID = "id";
   public static final String AUTHORIZATION_PROPERTY = "x-access-token";
   private static final String ACCESS_REFRESH = "Token expired. Please authenticate again!";
   private static final String ACCESS_INVALID_TOKEN = "Token invalid. Please authenticate again!";
   private static final String ACCESS_DENIED = "Not allowed to access this resource!";
   private static final String NO_TOKEN_PROVIDED = "No token provided!";
   private static final String ACCESS_FORBIDDEN = "Access forbidden!";

   @Override
   public void filter(ContainerRequestContext requestContext) {

      Method method = resourceInfo.getResourceMethod();

      if (!method.isAnnotationPresent(PermitAll.class)) {
         if (method.isAnnotationPresent(DenyAll.class)) {
            throw new ForbiddenException("FORBIDDEN - "+ACCESS_FORBIDDEN);
         }
         final MultivaluedMap<String, String> headers = requestContext.getHeaders();
         final List<String> authProperty = headers.get(AUTHORIZATION_PROPERTY); // = x-access-token

         if ((authProperty == null) || authProperty.isEmpty()) { // korisnik nije prosledio x-access-token header
            throw new NotAuthorizedException("UNAUTHORIZED - " + NO_TOKEN_PROVIDED );
         }
         String id = null;
         String jwt = authProperty.get(0);

         try {
            id = TokenSecurity.validateJwtToken(jwt);
         } catch (InvalidJwtException e) {
            throw new NotAuthorizedException("UNAUTHORIZED - " + ACCESS_INVALID_TOKEN );
         }

         User userSecurity = SecurityUtils.getUserSecurity(httpRequest.getSession(), id);
         if (userSecurity == null) { // u sesiji nema UserSecurity:
            throw new NotAuthorizedException("UNAUTHORIZED - " + ACCESS_DENIED );
         }

         if (!userSecurity.getToken().equals(jwt)) {
            throw new NotAuthorizedException("UNAUTHORIZED - " + ACCESS_REFRESH );
         }

         if (method.isAnnotationPresent(RolesAllowed.class)) {
            // get annotated roles
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

            if (!isUserAllowed(userSecurity.getRole().toString(), rolesSet)) {
               throw new NotAuthorizedException("UNAUTHORIZED - " + ACCESS_DENIED );
            }
         }
      }
   }
   private boolean isUserAllowed(final String userRole, final Set<String> rolesSet) {
      boolean isAllowed = false;
      if (rolesSet.contains(userRole)) {
         isAllowed = true;
      }
      return isAllowed;
   }
}