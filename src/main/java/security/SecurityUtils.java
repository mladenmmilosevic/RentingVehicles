package security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Context;

import helpentities.User;

public class SecurityUtils {

   @Context
   private HttpServletRequest httpRequest;

   public static User getUserSecurity(HttpSession session, String id) {
      User userSec = (User) session.getServletContext().getAttribute("user_sec_" + id);
      if (userSec == null) {
         throw new NotAuthorizedException("Unauthorized!");
      }
      return userSec;
   }

   public static void setUserSecurity(HttpSession session, User user_sec) {
      session.getServletContext().setAttribute("user_sec_" + user_sec.getId(), user_sec);
   }

   public static void removeUserSecurity(HttpSession session, String id) {
      session.getServletContext().removeAttribute("user_sec_" + id);
   }
}
