package abstractEntities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class DataReservation  {

   @Transient
   protected String dateStartStr;
   @Transient
   protected String dateEndStr;

   @Column(name = "date_end")
   @FutureOrPresent(message = "dateEnd must be in the present or future")
   protected Date dateEnd;

   @Column(name = "date_start")
   @FutureOrPresent(message = "dateStart must be in the present or future")
   protected Date dateStart;

   @Column(name = "price_day")
   @NotNull(message = "PriceDay must be set")
   protected Integer priceDay;

   public DataReservation() {
      super();
   }

   public DataReservation( Date dateEnd, Date dateStart, Integer priceDay) {
      super();
      this.dateEnd = dateEnd;
      this.dateStart = dateStart;
      this.priceDay = priceDay;
   }

   public DataReservation(String dateStartStr, String dateEndStr, Integer priceDay) {
      super();
      this.dateStartStr = dateStartStr;
      this.dateEndStr = dateEndStr;
      this.priceDay = priceDay;
   }

   public String getDateStartStr() {
      return dateStartStr;
   }

   public void setDateStartStr(String dateStartStr) throws ParseException {
      this.dateStartStr = dateStartStr;
      setDateStart(new SimpleDateFormat("dd.MM.yyyy").parse(dateStartStr));
   }

   public String getDateEndtStr() {
      return dateEndStr;
   }

   public void setDateEndStr(String dateEndStr) throws ParseException {
      this.dateEndStr = dateEndStr;
      setDateEnd(new SimpleDateFormat("dd.MM.yyyy").parse(dateEndStr));
   }

   public Date getDateEnd() {
      return dateEnd;
   }

   public void setDateEnd(Date dateEnd) {
      this.dateEnd = dateEnd;
   }

   public Date getDateStart() {
      return dateStart;
   }

   public void setDateStart(Date dateStart) {
      this.dateStart = dateStart;
   }

   public Integer getPriceDay() {
      return this.priceDay;
   }

   public void setPriceDay(Integer priceDay) {
      this.priceDay = priceDay;
   }

   @Override
   public String toString() {
      return " [dateEnd=" + dateEnd + ", dateStart=" + dateStart + ", priceDay=" + priceDay + "]";
   }

}
