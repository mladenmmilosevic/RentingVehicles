package entities;

import java.io.Serializable;
import java.util.List;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import abstractEntities.DataVehicles;


@Entity
@Table(name = "cars")
@NamedQuery(name = "Car.findAll", query = "SELECT new entities.Car(c.company,c.make ,c.model,c.modelYear,c.cubicCapacity,c.enginePower,c.engineNumber) FROM Car c ")
@NamedQuery(name = "Car.findByID", query = "SELECT new entities.Car(c.company,c.make ,c.model,c.modelYear,"
         + "c.cubicCapacity,c.enginePower,c.engineNumber) FROM Car c where c.engineNumber=:en")
@JsonbPropertyOrder(value = {
         "engineNumber", "make", "model",
         "modelYear", "cubicCapacity", "enginePower", "broj"
})
@NamedQuery(name = "Car.MostRentedCars", query = "SELECT new entities.Car(c.company,c.engineNumber,c.make ,c.model,count(c.engineNumber) ) "
         + "from  Reservation r join r.car c " + "group by c.company, c.engineNumber,c.make ,c.model "
         + "having count(c.engineNumber) > 0 ORDER by count(c.engineNumber) desc")
public class Car extends DataVehicles implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @Column(name = "engine_number")
   @NotBlank(message = "EngineNumber must be set")
   @Size(min = 10, max = 10, message = "engineNumber  must have 10 characters")

   private String engineNumber;

   @ManyToOne
   @JoinColumn(name = "company_id")
   private Company company;

   @OneToMany(mappedBy = "car", cascade = {
            CascadeType.ALL
   })
   @JsonbTransient
   private List<Reservation> reservations;

   @Transient
   private Long broj;

   @Transient
   private String tin;

   public Car() {
   }

   public Car(Company company, String engineNumber, String carMake, String carModel, Long broj) {
      super(carMake, carModel);
      this.company = company;
      this.engineNumber = engineNumber;
      this.broj = broj;
   }

   public Car(Company company, String make, String model, String modelYear, Integer cubicCapacity,
            Integer enginePower, String engineNumber) {
      super(make, model, modelYear, cubicCapacity, enginePower);
      this.company = company;
      this.engineNumber = engineNumber;
   }

   public String getTin() {
      return tin;
   }

   public void setTin(String tin) {
      this.tin = tin;
   }

   public Long getBroj() {
      return broj;
   }

   public void setBroj(Long broj) {
      this.broj = broj;
   }

   public String getEngineNumber() {
      return this.engineNumber;
   }

   public void setEngineNumber(String engineNumber) {
      this.engineNumber = engineNumber;
   }

   public Company getCompany() {
      Company company = new Company();

      company.setCompanyName(this.company.getCompanyName());
      company.setEmail(this.company.getEmail());

      return company;
   }

   public void setCompany(Company company) {
      this.company = company;
   }

   public List<Reservation> getReservations() {
      return this.reservations;
   }

   public void setReservations(List<Reservation> reservations) {
      this.reservations = reservations;
   }

   public Reservation addReservation(Reservation reservation) {
      getReservations().add(reservation);
      reservation.setCar(this);

      return reservation;
   }

   public Reservation removeReservation(Reservation reservation) {
      getReservations().remove(reservation);
      reservation.setCar(null);

      return reservation;
   }

   @Override
   public String toString() {
      return "Car [engineNumber=" + engineNumber + ", company=" + company + " " + super.toString() + "]";
   }

}
