package services;

import java.util.List;

import javax.ejb.Stateless;

import entities.Reservation;


@Stateless
public class ReservationService extends BaseService{

   public List<Reservation> getAllReservations() {
      List<Reservation> reservations = entityManager.createNamedQuery
               ("Reservation.findAll", Reservation.class).getResultList();
      return reservations;
   }
}
