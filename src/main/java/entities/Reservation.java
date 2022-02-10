package entities;

import java.io.Serializable;
import java.util.Date;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import abstractEntities.DataReservation;

@Entity
@Table(name = "reservations")
@NamedQuery(name = "Reservation.findAll", query = "SELECT new entities.Reservation(r.dateStart,r.dateEnd,r.priceDay,r.reservationId, r.car,r.customer) FROM Reservation r")
@JsonbPropertyOrder(value = {
         "reservation_id", "dateStart", "dateEnd", "priceDay", "car", "customer"
})
public class Reservation extends DataReservation implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @Column(name = "reservation_id")
   @NotNull(message = "ReservationID must be set")
   private Integer reservationId;

   @Transient
   private String userId;

   @Transient
   private String carId;

   @ManyToOne
   @JoinColumn(name = "car_id")
   private Car car;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private Customer customer;

   public Reservation() {
   }

   public Reservation(Date dateEnd, Date dateStart, Integer priceDay, Integer reservationId, Car car, Customer customer) {
      super(dateEnd, dateStart, priceDay);
      this.reservationId = reservationId;
      this.car = car;
      this.customer = customer;
   }

   public Reservation(String dateStartStr, String dateEndStr, Integer priceDay,
            Integer reservationId, Car car, Customer customer) {
      super(dateStartStr, dateEndStr, priceDay);
      this.reservationId = reservationId;
      this.car = car;
      this.customer = customer;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getCarId() {
      return carId;
   }

   public void setCarId(String carId) {
      this.carId = carId;
   }

   public Integer getReservationId() {
      return this.reservationId;
   }

   public void setReservationId(Integer reservationId) {
      this.reservationId = reservationId;
   }

   public Car getCar() {

      Car car = new Car();
      car.setMake(this.car.getMake());
      car.setModel(this.car.getModel());
      car.setModelYear(this.car.getModelYear());
      car.setCompany(this.car.getCompany());
      return this.car;
   }

   public void setCar(Car car) {
      this.car = car;
   }

   public Customer getCustomer() {

      Customer customer = new Customer();
      customer.setFirstName(this.customer.getFirstName());
      customer.setLastName(this.customer.getLastName());
      customer.setEmail(this.customer.getEmail());

      return customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   @Override
   public String toString() {
      return "Reservation [reservationId=" + reservationId + super.toString() + ", car=" + car + ", customer=" + customer + "]";
   }

}
