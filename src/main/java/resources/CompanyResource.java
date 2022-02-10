package resources;

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

import entities.Company;
import services.CompanyService;

@Path("company")
@Consumes({
   MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN
})
@Produces({
   MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN
})
public class CompanyResource {

   @Inject
   CompanyService service;

   @GET
   @Path("getAllCarsByCompany/{tin}")
   @RolesAllowed({
      "ADMIN", "CUSTOMER", "VIP"
   })
   public Response getAllCarsByCompany(@PathParam("tin") String tin) {

      Company company = (Company) service.get(Company.class, tin);
      if (company != null) {
         return Response.ok(service.getAllCarsByCompany(company)).build();
      } else {
         throw new NotFoundException("Company with id " + tin + " not found");
      }
   }

   @GET
   @Path("getAllCompanies")
   @RolesAllowed({
      "ADMIN", "CUSTOMER", "VIP"
   })
   public Response getAllCompanies() {
      return Response.ok(service.getAllCompanies()).build();
   }

   @GET
   @Path("getCompanyByTin/{tin}")
   @RolesAllowed({
      "ADMIN", "CUSTOMER", "VIP"
   })
   public Response getCompanyByTin(@PathParam("tin") String tin) {
      Company company = (Company) service.get(Company.class, tin);
      if (company != null) {
         return Response.ok(company).build();
      } else {
         throw new NotFoundException("Company with id " + tin + " not found");
      }
   }

   @POST
   @RolesAllowed({
      "ADMIN"
   })
   public Response createCompany(Company company) {
      Company comp = (Company) service.get(Company.class, company.getTin());
      if (comp != null) {
         throw new BadRequestException("Company with an tin already exists");
      }
      service.create(company);
      return Response.status(Response.Status.CREATED).build();
   }

   @PUT
   @Path("{tin}")
   @RolesAllowed({
      "ADMIN"
   })
   public Response updateCompany(@PathParam("tin") String tin, Company company) {
      Company comp = (Company) service.get(Company.class, tin);
      if (comp == null) {
         throw new NotFoundException("Company with id " + tin + " not found");
      }

      if (company.getCity() != null) {
         comp.setCity(company.getCity());
      }
      if (company.getCompanyName() != null) {
         comp.setCompanyName(company.getCompanyName());
      }
      if (company.getEmail() != null) {
         comp.setEmail(company.getEmail());
      }
      if (company.getPhone() != null) {
         comp.setPhone(company.getPhone());
      }
      service.update(comp);
      return Response.status(Response.Status.OK).build();
   }

   @DELETE
   @Path("{tin}")
   @RolesAllowed({
      "ADMIN"
   })
   public Response deleteCompany(@PathParam("tin") String tin) {
      Company company = (Company) service.get(Company.class, tin);
      if (company == null) {
         throw new NotFoundException("Company with id " + tin + " not found");
      }
      service.delete(company);
      return Response.status(Response.Status.NO_CONTENT).build();
   }

}
