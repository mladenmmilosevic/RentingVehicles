package exception;

public class ErrorMessage {

   private String errorMessage;
   private int  errorCode;

   public ErrorMessage() {

   }

   public ErrorMessage(String errorMessage, int errorCode) {
      super();
      this.errorMessage = errorMessage;
      this.errorCode = errorCode;
   }

   public String getErrorMessage() {
      return errorMessage;
   }

   public void setErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
   }

   public int  getErrorCode() {
      return errorCode;
   }


   public void setErrorCode(int errorCode) {
      this.errorCode = errorCode;
   }

   @Override
   public String toString() {
      return "ErrorMessage [errorMessage=" + errorMessage + ", errorCode=" + errorCode + "]";
   }

}
