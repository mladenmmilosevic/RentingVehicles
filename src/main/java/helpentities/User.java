package helpentities;

import enums.Role;

public class User {


   private String id = null;
   private String email = null;
   private String firstname = null;
   private String lastname = null;
   private String password = null;
   private String token = null;
   private Role role = null;


   public User() {
   }

   public User(String email, String firstname, String lastname) {
      this.email = email;
      this.firstname = firstname;
      this.lastname = lastname;
   }

   public User(String id, String email, String firstname, String lastname) {
      this(email, firstname, lastname);
      this.id = id;
   }

   public User(String id, String email, String firstname, String lastname, String password, String token,
            Role role) {
      super();
      this.id = id;
      this.email = email;
      this.firstname = firstname;
      this.lastname = lastname;
      this.password = password;
      this.token = token;
      this.role = role;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   @Override
   public String toString() {
      return "User [id=" + id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname + "]";
   }

}
