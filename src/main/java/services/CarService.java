package services;

import java.util.List;

import javax.ejb.Stateless;

import entities.Car;

@Stateless
public class CarService  extends BaseService{

   public List<Car> getMostRentedCars() {
      return entityManager.createNamedQuery("Car.MostRentedCars", Car.class)
               .getResultList();

   }

   public List<Car> getAllCars() {
      return entityManager.createNamedQuery("Car.findAll", Car.class).getResultList();

   }
}
