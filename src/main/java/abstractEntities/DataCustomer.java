package abstractEntities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import enums.Gender;
import enums.Role;

@MappedSuperclass
@JsonbPropertyOrder(value = {"firstName", "lastName","email", "birthday", "gender", "username", "password", "role"})
public abstract class DataCustomer {

   @Transient
   protected String dateStr;

   @Column(name = "birthday")
   @NotNull(message = "Due must be set")
   @PastOrPresent(message = "birthday must be in the past or present")
   protected Date birthday;

   @Column(name = "email")
   @NotBlank(message = "Email must be set")
   @Size(min =	3, max = 30)
   protected String email;

   @Column(name="first_name")
   @NotBlank(message = "FirstName must be set")
   protected String firstName;

   @Column(name = "gender")
   @NotNull(message = "Gender must be set")
   @Enumerated(EnumType.STRING)
   protected Gender gender;

   @Column(name="last_name")
   @NotBlank(message = "LastName must be set")
   protected String lastName;

   @Column(name="password")
   @NotBlank(message = "Password must be set")
   protected String password;

   @Column(name = "role")
   @Enumerated(EnumType.STRING)
   protected Role role;

   @Column(name="username")
   @NotBlank(message = "Username must be set")
   protected String username;


   public DataCustomer() {
      super();
   }

   public DataCustomer(String firstName, String lastName) {
      super();
      this.firstName = firstName;
      this.lastName = lastName;
   }

   public DataCustomer(String password, Role role) {
      super();
      this.password = password;
      this.role = role;
   }

   public DataCustomer(String birthday, String email, String firstName, Gender gender, String lastName) {
      super();
      this.dateStr = birthday;
      this.email = email;
      this.firstName = firstName;
      this.gender = gender;
      this.lastName = lastName;
   }

   public DataCustomer(String birthday, String email, String firstName, Gender gender, String lastName, String password,
            Role role, String username) {
      super();
      this.dateStr = birthday;
      this.email = email;
      this.firstName = firstName;
      this.gender = gender;
      this.lastName = lastName;
      this.password = password;
      this.role = Role.CUSTOMER;
      this.username = username;
   }


   public DataCustomer(Date birthday,String email, String firstName, Gender gender, String lastName) {
      super();
      this.birthday = birthday;
      this.email = email;
      this.firstName = firstName;
      this.gender = gender;
      this.lastName = lastName;
   }

   public String getDateStr() {
      return dateStr;
   }

   public void setDateStr(String dateStr) throws ParseException {
      this.dateStr = dateStr;
      setBirthday(new SimpleDateFormat("dd.MM.yyyy").parse(dateStr));
   }


   public Date getBirthday() {
      return birthday;
   }

   public void setBirthday(Date birthday) {
      this.birthday = birthday;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public Gender getGender() {
      return gender;
   }

   public void setGender(Gender gender) {
      this.gender = gender;
   }

   public String getLastName() {
      return lastName;
   }


   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {

      this.password = password;
   }

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   @Override
   public String toString() {
      return " [birthday=" + birthday + ", email=" + email + ", firstName=" + firstName + ", gender=" + gender
               + ", lastName=" + lastName + ", username=" + username + "]";
   }

}
