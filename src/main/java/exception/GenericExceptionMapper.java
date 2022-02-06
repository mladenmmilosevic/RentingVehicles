package exception;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

   @Override
   public Response toResponse(Exception exception) {

      ErrorMessage errorMessage = new ErrorMessage();

      if(exception instanceof BadRequestException) {
         errorMessage.setErrorMessage(exception.getMessage());
         errorMessage.setErrorCode(400);
         return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
      }
      else if(exception instanceof NotAuthorizedException) {
         errorMessage.setErrorMessage(exception.getMessage());
         errorMessage.setErrorCode(401);
         return Response.status(Status.UNAUTHORIZED).entity(errorMessage).build();
      }
      else if(exception instanceof ForbiddenException) {
         errorMessage.setErrorMessage(exception.getMessage());
         errorMessage.setErrorCode(403);
         return Response.status(Status.FORBIDDEN).entity(errorMessage).build();
      }
      else if(exception instanceof NotFoundException) {
         errorMessage.setErrorMessage(exception.getMessage());
         errorMessage.setErrorCode(404);
         return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
      }
      else if(exception instanceof Exception) {
         errorMessage.setErrorMessage("CONFLICT");
         errorMessage.setErrorCode(409);
         return Response.status(Status.CONFLICT).entity(errorMessage).build();
      }

      errorMessage.setErrorMessage("INTERNAL_SERVER_ERROR " + exception.getMessage());
      errorMessage.setErrorCode(500);
      System.err.println("Global exception " + errorMessage.getErrorMessage());
      return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();

   }
}

