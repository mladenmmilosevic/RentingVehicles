package resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import entities.Customer;
import enums.Role;
import helpentities.ChangePassword;
import helpentities.Credentials;
import helpentities.User;
import security.SecurityUtils;
import security.TokenSecurity;
import services.CustomerService;


@Path("customer")
@Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
public class CustomerResource {

   @Inject
   CustomerService service;

   @Context
   HttpServletRequest request;

   @Context
   private ResourceContext context;

   @Inject
   Pbkdf2PasswordHash pwdHash ;

   @PostConstruct
   public void init() {
      Map<String, String> parameters = new HashMap<>();
      parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
      parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
      parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
      pwdHash.initialize(parameters);
   }

   @GET
   @RolesAllowed({"customer","vip","admin"})
   @Path("getAllCustomers")
   public Response getAllCustomer() {
      return Response.ok(service.getAllCustomers()).build(); //
   }

   @GET
   @RolesAllowed({"customer","vip","admin"})
   @Path("getVipCustomers")
   public Response getVipCustomer() {
      return Response.ok(service.getVipCustomers()).build();
   }

   @GET
   @RolesAllowed({"customer","vip","admin"})
   @Path("name/{name}")
   public Response getCustomerByName(@PathParam("name") String name) {
      return Response.ok(service.getCustomersByName(name)).build(); //
   }

   @GET
   @RolesAllowed({"customer","vip","admin"})
   @Path("getCustomerByUIN/{uin}")
   public Response getCustomerByUIN(@PathParam("uin") String uin) {
      Customer customer = (Customer)service.get(Customer.class,uin);
      if (customer != null) {
         return Response.ok(customer).build(); //
      } else {
         throw new NotFoundException("Customer with id "+uin+" not found" );
      }
   }

   @GET
   @RolesAllowed({"customer","vip","admin"})
   @Path("cust/{uin}/ren/{id}")
   public Response delRezbyCustomer(@PathParam("uin") String uin, @PathParam("id") int id) {
      service.delRezbyCustomer(uin, id);
      return Response.ok(service.get(Customer.class,uin)).build(); //
   }

   @POST
   //@RolesAllowed({"customer","vip","admin"})
   @PermitAll
   public Response createCustomer(Customer customer) {
      Customer cust=(Customer)service.get(Customer.class,customer.getUin());
      if (cust != null) {
         throw new BadRequestException("Customer with an uin already exists");
      }
      customer.setPassword(passwordHash(customer.getPassword()));
      customer.setRole(Role.customer);
      service.create(customer);
      return Response.status(Response.Status.CREATED).build();
   }

   @PUT
   @RolesAllowed({"customer","vip","admin"})
   @Path("{uin}")
   public Response updateCustomer(@PathParam("uin") String uin, Customer customer) {
      Customer cust=(Customer)service.get(Customer.class,uin);
      if (cust==null) {
         throw new NotFoundException("Customer with id "+uin+" not found" );
      }
      if(customer.getPassword()!=null) {
         cust.setPassword(passwordHash(customer.getPassword()));
      }
      if(customer.getFirstName()!=null) {
         cust.setFirstName(customer.getFirstName());
      }
      if(customer.getLastName()!=null) {
         cust.setLastName(customer.getLastName());
      }
      if(customer.getEmail()!=null) {
         cust.setEmail(customer.getEmail());
      }
      if(customer.getUsername()!=null) {
         cust.setUsername(customer.getUsername());
      }
      if(customer.getRole()!=null) {
         cust.setRole(customer.getRole());
      }

      service.update(cust);
      return Response.status(Response.Status.OK).build();
   }

   @DELETE
   @RolesAllowed({"admin"})
   @Path("{uin}")
   public Response deleteCustomer(@PathParam("uin") String uin) {
      Customer customer =(Customer) service.get(Customer.class,uin);
      if (customer == null) {
         throw new NotFoundException("Customer with id "+uin+" not found" );
      }
      service.delete(customer);
      return Response.status(Response.Status.NO_CONTENT).build();
   }

   @POST
   @Path("login")
   @PermitAll
   public Response loginCustomer(Credentials credentials) {
      Customer customer = service.getCustomersByNameEquals(credentials.getUsername());
      String token;
      if(customer == null)
      {
         throw new NotFoundException("Customer with username "+credentials.getUsername()+" not found" );
      }

      boolean ver = pwdHash.verify(credentials.getPassword().toCharArray(),customer.getPassword());
      if(!ver) // !customer.getPassword().equals(pass)
      {
         throw new BadRequestException("Wrong pass");
      }
      try {
         token=TokenSecurity.generateJwtToken(customer.getUin());
      }
      catch(Exception ex) {
         throw new BadRequestException("error during JWT generation");
      }

      User user = new User(customer.getUin(),customer.getEmail() , customer.getFirstName(), customer.getLastName(), customer.getPassword(), token, customer.getRole());
      try {
         SecurityUtils.setUserSecurity(request.getSession(), user);
      }
      catch(Exception ex) {
         throw new BadRequestException("Set user security");
      }
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("x-access-token", token);
      return Response.status(Status.OK).entity(map).build();
   }

   @Path("{uin}/changepass")
   @RolesAllowed({"customer","vip","admin"})
   @POST
   public Response changePassCustomer(@PathParam("uin") String uin,ChangePassword cp) {
      Customer customer =(Customer) service.get(Customer.class,uin);
      if(customer == null)
      {
         throw new NotFoundException("Customer with id "+uin+" not found" );
      }
      cp.setNewpassword(passwordHash(cp.getNewpassword()));

      boolean ver = pwdHash.verify(cp.getOldpassword().toCharArray(),
               customer.getPassword());
      if(!ver)  //!customer.getPassword().equals(pass)
      {
         throw new BadRequestException("Passwords are not the same");
      }
      customer.setPassword(cp.getNewpassword());
      service.update(customer);
      return Response.ok().build();
   }
   @GET
   @RolesAllowed({"customer","vip","admin"})
   @Path("/logout")
   public Response logout(@Context HttpHeaders headers) {
      SecurityUtils.removeUserSecurity(request.getSession(), getId(headers));
      return Response.status(Status.OK).entity("You are logged out!").build();
   }

   private String getId(HttpHeaders headers) {
      // get the email we set in AuthenticationFilter
      List<String> id = headers.getRequestHeader("id");
      if ((id == null) || (id.size() != 1)) {
         throw new NotAuthorizedException("Unauthorized!");
      }
      return id.get(0);
   }

   public String  passwordHash(String password) {
      String string= pwdHash.generate(password.toCharArray());
      return string;
   }
}

