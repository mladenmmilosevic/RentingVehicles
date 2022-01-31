package security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Context;
import helpentities.User;

public class SecurityUtils {
	
	@Context
	   private HttpServletRequest httpRequest;
	
	  /* Vraca MD5 enkodovanu vrednost stringa str
	    *
	    * @param str
	    * @return
	    
	      public static String encode_MD5(String str) {
		   
		   
		  System.out.println("Sifra   " + str ); 
		   
	      MessageDigest md;
	      try {
	         md = MessageDigest.getInstance("MD5");
	      } catch (NoSuchAlgorithmException e) {
	         throw new RuntimeException("greska u encode_MD5(): " + e.getMessage());
	      }
	      md.update(str.getBytes());
	      byte[] digest = md.digest();
	      String result = bytesToHex(digest);
	      System.out.println("encode_MD5() za " + str + " vraca: " + result);
	      return result;
	   }

	   private static String bytesToHex(byte[] bytes) {
	      StringBuilder sb = new StringBuilder();
	      for (byte b : bytes) {
	         sb.append(String.format("%02x", b));
	      }
	      return sb.toString();
	   }
	   */
	   public static User getUserSecurity(HttpSession session, String id) { 
		    //  System.out.println("getUserSecurity(), session = " + session);
		      User userSec = (User) session.getServletContext().getAttribute("user_sec_" + id);
		      if (userSec == null) {
		         System.err.println("***getUserSecurity() greska, userSec je null!");
		         throw new NotAuthorizedException("Unauthorized!");
		      }
		      return userSec;
		   }

		   public static void setUserSecurity(HttpSession session, User user_sec) {
		      //System.out.println("setUserSecurity(), session = " + session);
		      // session.setAttribute("user_sec", user_sec);
		      session.getServletContext().setAttribute("user_sec_" + user_sec.getId() , user_sec);
		   }

		   public static void removeUserSecurity(HttpSession session, String id) {
		      session.getServletContext().removeAttribute("user_sec_" + id);
		   }
}
