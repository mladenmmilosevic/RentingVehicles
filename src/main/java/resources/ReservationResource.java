package resources;

import java.text.ParseException;

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
import entities.Customer;
import entities.Reservation;
import services.ReservationService;

@Path("rent")
@Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
public class ReservationResource {

   @Inject
   ReservationService service;


   @GET
   @RolesAllowed({ "ADMIN","CUSTOMER","VIP" })
   public Response getReservations() {
      return Response.ok(service.getAllReservations()).build();
   }

   @GET
   @RolesAllowed({ "ADMIN","CUSTOMER","VIP" })
   @Path("getReservationByID/{id}")
   public Response getReservationByID(@PathParam("id") int id) {
      Reservation reservation = (Reservation) service.get(Reservation.class,id);
      if(reservation != null) {
         return Response.ok(reservation).build(); //
      } else {
         throw new NotFoundException("Reservation with id "+id+" not found" );
      }
   }

   @POST
   @RolesAllowed({ "ADMIN","CUSTOMER","VIP" })
   public Response createReservation(Reservation reservation) throws ParseException {
      Reservation pom=(Reservation)service.get(Reservation.class,reservation.getReservationId());
      if (pom!= null) {
         throw new BadRequestException("Reservation with an id already exists");
      }

      Car car =(Car)service.get( Car.class, reservation.getCarId());
      Customer customer =(Customer)service.get( Customer.class, reservation.getUserId());

      Reservation res =new Reservation();
      res.setCar(car);
      res.setCustomer(customer);

      res.setDateEndStr(reservation.getDateEndtStr());
      res.setDateStartStr(reservation.getDateStartStr());

      res.setPriceDay(reservation.getPriceDay());
      res.setReservationId(reservation.getReservationId());
      service.create(res);
      return Response.status(Response.Status.CREATED).build();
   }

   @PUT
   @Path("{id}")
   @RolesAllowed({ "ADMIN","CUSTOMER","VIP" })
   public Response updateReservation(@PathParam("id") int id, Reservation reservation) throws ParseException {
      Reservation res=(Reservation)service.get(Reservation.class,id);
      if (res==null) {
         throw new NotFoundException("Reservation with id "+id+" not found" );
      }

      if(reservation.getCarId()!=null) {
         Car car=(Car)service.get(Car.class, reservation.getCarId());
         if(car==null) {
            throw new NotFoundException("Car with id "+reservation.getCarId()+" not found");
         }
         res.setCar(car);
      }
      if(reservation.getDateEndtStr()!=null) {
         res.setDateEndStr(reservation.getDateEndtStr());
      }

      if(reservation.getDateStartStr()!=null) {
         res.setDateStartStr(reservation.getDateStartStr());
      }
      if(reservation.getPriceDay()!=null) {
         res.setPriceDay(reservation.getPriceDay());
      }

      service.update(res);
      return Response.status(Response.Status.OK).build();
   }

   @DELETE
   @Path("{id}")
   @RolesAllowed({ "ADMIN","CUSTOMER","VIP" })
   public Response deleteReservation(@PathParam("id") int id) {
      Reservation reservation = (Reservation) service.get(Reservation.class,id);
      if(reservation == null) {
         throw new NotFoundException("Reservation with id "+id+" not found" );
      }
      service.delete(reservation);
      return Response.status(Response.Status.NO_CONTENT).build();
   }

}
