package entities;

import java.io.Serializable;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * The persistent class for the companies database table.
 *
 */
@Entity
@Table(name="companies")
@NamedQuery(name="Company.findAll", query="SELECT new entities.Company(c.tin,c.email,c.city,c.phone,c.companyName) FROM Company c")
public class Company implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @Column(name = "tin")
   @NotBlank(message = "Tin must be set")
   @Size(min = 10,max = 10, message = "tin  must have 10 characters")//bin validation greska razlicit broj cifara
   private String tin;

   @Column(name="city")
   @NotBlank(message = "City must be set")
   private String city;

   @Column(name="company_name")
   @NotBlank(message = "CompanyName must be set")
   private String companyName;

   @Column(name="email")
   @NotBlank(message = "Email must be set")
   private String email;

   @Column(name="phone")
   @NotBlank(message = "Phone must be set")
   private String phone;

   //bi-directional many-to-one association to Car
   @OneToMany(mappedBy="company", cascade = { CascadeType.ALL })
   @JsonbTransient
   private List<Car> cars;

   public Company() {
   }

   public Company( String tin,String email,String city,
            String phone, String companyName) {
      super();
      this.tin = tin;
      this.email = email;
      this.city = city;
      this.phone = phone;
      this.companyName = companyName;
   }



   public List<Car> getCars() {
      return cars;
   }

   public String getTin() {
      return this.tin;
   }

   public void setTin(String tin) {
      this.tin = tin;
   }

   public String getCity() {
      return this.city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getCompanyName() {
      return this.companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public void setCars(List<Car> cars) {
      this.cars = cars;
   }

   @Override
   public String toString() {
      return "Company [tin=" + tin + ", city=" + city + ", companyName=" + companyName + ", email=" + email
               + ", phone=" + phone +  "]";
   }

}