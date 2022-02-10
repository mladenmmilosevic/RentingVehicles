package resources;

import java.util.ArrayList;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Car;
import entities.Company;
import entities.Reservation;
import services.CarService;

@Path("car")
@Consumes({
   MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN
})
@Produces({
   MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN
})
public class CarResource {

   @Inject
   CarService service;

   @GET
   @RolesAllowed({
      "ADMIN", "CUSTOMER", "VIP"
   })
   @Path("getMostRentedCars")
   public Response getMostRentedCars() {
      return Response.ok(service.getMostRentedCars()).build();
   }

   @GET
   @RolesAllowed({
      "ADMIN", "CUSTOMER", "VIP"
   })
   @Path("getAllCars")
   public Response getAllCars() {
      return Response.ok(service.getAllCars()).build();
   }

   @GET
   @RolesAllowed({
      "ADMIN", "CUSTOMER", "VIP"
   })
   @Path("getCarByEN/{en}")
   public Response getCarByEN(@PathParam("en") String en) {

      Car car = (Car) service.get(Car.class, en);
      if (car != null) {
         return Response.ok(car).build();
      } else {
         throw new NotFoundException("Car with id " + en + " not found");
      }
   }

   @POST
   @RolesAllowed({
      "ADMIN"
   })
   public Response createCar(Car car) {

      Car pom = (Car) service.get(Car.class, car.getEngineNumber());
      if (pom != null) {
         throw new BadRequestException("Car with an egine number already exists");
      }

      Company company = (Company) service.get(Company.class, car.getTin());
      car.setCompany(company);
      car.setReservations(new ArrayList<Reservation>());
      service.create(car);
      return Response.status(Response.Status.CREATED).build();
   }

   @PUT
   @Path("{en}")
   @RolesAllowed({
      "ADMIN"
   })
   public Response updateCar(@PathParam("en") String en, Car car) {
      Car c = (Car) service.get(Car.class, en);
      if (c == null) {
         throw new NotFoundException("Car with id " + en + " not found");
      }

      if (car.getEnginePower() != null) {
         c.setEnginePower(car.getEnginePower());
      }
      if (car.getCubicCapacity() != null) {
         c.setCubicCapacity(car.getCubicCapacity());
      }
      service.update(c);
      return Response.status(Response.Status.OK).build();
   }

   @DELETE
   @Path("{en}")
   @RolesAllowed({
      "ADMIN"
   })
   public Response deleteCar(@PathParam("en") String en) {

      Car car = (Car) service.get(Car.class, en);
      if (car == null) {
         throw new NotFoundException("Car with id " + en + " not found");
      }
      service.delete(car);
      return Response.status(Response.Status.NO_CONTENT).build();
   }

}
