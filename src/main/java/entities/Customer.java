package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import abstractEntities.DataCustomer;
import enums.Gender;
import enums.Role;


/**
 * The persistent class for the customers database table.
 *
 */@Entity
 @Table(name = "customers")
 @NamedQuery(name = "Customer.findAll", query = "SELECT new entities.Customer(c.birthday,c.email,c.firstName,c.gender,c.lastName) FROM Customer c")
 @NamedQuery(name="Customer.findCustomerByName", query="SELECT new entities.Customer(c.birthday,c.email,c.firstName,c.gender,c.lastName) "
          + "FROM Customer c WHERE c.firstName LIKE :full ")
 @NamedQuery(name="Customer.findCustomerByUserName", query="SELECT new entities.Customer(c.password,c.role,c.uin)"
          + " FROM Customer c WHERE c.username like :full ")
 @NamedQuery(name="Customer.VipCustomers", query="SELECT new entities.Customer(c.firstName,c.lastName,c.uin, SUM((r.dateEnd-r.dateStart)*r.priceDay)) "
          + "FROM  Reservation r join r.customer c "
          + "GROUP by c.uin, c.firstName,c.lastName "
          + "HAVING SUM((r.dateEnd-r.dateStart)*r.priceDay) > 0 "
          + "ORDER BY SUM(((r.dateEnd-r.dateStart)*r.priceDay)) desc")

 /*@NamedNativeQuery(name = "Vip_Customers",
 query = "select c.uin as uin ,c.first_name  as firstName ,c.last_name  as lastName, sum((r.date_end - r.date_start)* r.price_day ) as money_spent "
 + "from customers as c join  reservations as r on c.uin = r.user_id  "
 + "GROUP by c.uin, c.first_name,c.last_name "
 + "having sum((r.date_end - r.date_start)* r.price_day )   > 0 "
 + "ORDER by money_spent desc", resultSetMapping = "getVip_Customers")
@SqlResultSetMapping(name = "getVip_Customers", classes = {
 @ConstructorResult(columns = {
       @ColumnResult(name = "firstName"),
       @ColumnResult(name = "lastName"),
       @ColumnResult(name = "uin"),
       @ColumnResult(name = "money_spent", type = Integer.class) }, targetClass = Customer.class) })*/

 public class Customer extends DataCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "uin")
    @Size(min = 10,max = 10, message = "uin  must have 10 characters")
    @NotBlank(message = "Uin must be set")
    private String uin;

    //bi-directional many-to-one association to Reservation
    @OneToMany(mappedBy="customer",cascade = { CascadeType.ALL })
    @JsonbTransient
    private List<Reservation> reservations;

    @Transient
    private Long money_spent;

    public Customer() {
    }


    public Customer(String firstName, String lastName, String uin, Long money_spent) {
       super(firstName, lastName);
       this.uin = uin;
       this.money_spent = money_spent;
    }

    public Customer(String birthday, String email, String firstName, Gender gender, String lastName,String uin) {
       super(birthday, email, firstName, gender, lastName);
       this.uin = uin;
    }

    public Customer(String password, Role role, String uin) {
       super(password, role);
       this.uin = uin;
    }

    public Customer(Date birthday, String email, String firstName, Gender gender, String lastName) {
       super(birthday, email, firstName, gender, lastName);
    }


    public Long getMoney_spent() {
       return money_spent;
    }

    public void setMoney_spent(Long money_spent) {
       this.money_spent = money_spent;
    }

    public String getUin() {
       return this.uin;
    }

    public void setUin(String uin) {
       this.uin = uin;
    }

    public List<Reservation> getReservations() {
       return this.reservations;
    }

    public void setReservations(List<Reservation> reservations) {
       this.reservations = reservations;
    }

    public Reservation addReservation(Reservation reservation) {
       getReservations().add(reservation);
       reservation.setCustomer(this);

       return reservation;
    }

    public Reservation removeReservation(Reservation reservation) {
       getReservations().remove(reservation);
       reservation.setCustomer(null);

       return reservation;
    }

    @Override
    public String toString() {
       return "Customer [uin=" + uin + ", reservations=" + reservations + ", money_spent=" + money_spent + super.toString() +"]";
    }

 }