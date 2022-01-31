package abstractEntities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class DataVehicles {

   @Column(name = "make")
   @NotBlank(message = "Make must be set")
   protected String make;

   @Column(name = "model")
   @NotBlank(message = "Model must be set")
   protected String model;

   @Column(name = "model_year")
   @NotBlank(message = "Model_year must be set")
   protected String modelYear;

   @Column(name = "cubic_capacity")
   @NotNull(message = "CubicCapacity must be set")
   protected Integer cubicCapacity;

   @Column(name = "engine_power")
   @NotNull(message = "EnginePower must be set")
   protected Integer enginePower;

   public DataVehicles() {
      super();
   }

   public DataVehicles(String make, String model) {
      super();
      this.make = make;
      this.model = model;
   }



   public DataVehicles( String make,String model, String modelYear,
            Integer cubicCapacity,Integer enginePower) {
      super();
      this.make = make;
      this.model = model;
      this.modelYear = modelYear;
      this.cubicCapacity = cubicCapacity;
      this.enginePower = enginePower;
   }

   public String getMake() {
      return make;
   }

   public void setMake(String make) {
      this.make = make;
   }

   public String getModel() {
      return model;
   }

   public void setModel(String model) {
      this.model = model;
   }

   public String getModelYear() {
      return modelYear;
   }

   public void setModelYear(String modelYear) {
      this.modelYear = modelYear;
   }

   public Integer getCubicCapacity() {
      return cubicCapacity;
   }

   public void setCubicCapacity(Integer cubicCapacity) {
      this.cubicCapacity = cubicCapacity;
   }

   public Integer getEnginePower() {
      return enginePower;
   }

   public void setEnginePower(Integer enginePower) {
      this.enginePower = enginePower;
   }


   @Override
   public String toString() {
      return " [make=" + make + ", model=" + model + ", modelYear=" + modelYear + ", cubicCapacity="
               + cubicCapacity + ", enginePower=" + enginePower + "]";
   }

}
