package services;

import java.util.List;

import javax.ejb.Stateless;

import entities.Customer;
import entities.Reservation;

@Stateless
public class CustomerService extends BaseService{

   public List<Customer> getCustomersByName(String name) {
      List<Customer> Customers = entityManager.createNamedQuery("Customer.findCustomerByName", Customer.class)
               .setParameter("full", "%" + name + "%").getResultList();
      return Customers;
   }

   public Customer getCustomersByNameEquals(String name) {
      List<Customer>customers= entityManager.createNamedQuery("Customer.findCustomerByUserName", Customer.class)
               .setParameter("full",  name ).getResultList();
      Customer  customer =null;

      if((customers!= null) &&  (customers.size()==1))
      {
         customer = customers.get(0);
      }
      return customer;
   }
   public List<Customer> getAllCustomers() {
      return entityManager.createNamedQuery("Customer.findAll", Customer.class).getResultList();

   }

   public List<Customer> getVipCustomers() {

      return entityManager.createNamedQuery("Customer.VipCustomers",Customer.class).getResultList();

   }

   public void delRezbyCustomer(String uin, int id) {
      Customer customer = (Customer) get(Customer.class,uin);
      List<Reservation> reservations =customer.getReservations();
      for (Reservation r : reservations) {
         if (r.getReservationId() == id) {
            customer.getReservations().remove(r);
            entityManager.remove(r);
            break;
         }
      }
   }

}
