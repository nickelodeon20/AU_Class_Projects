import java.util.Scanner;

public class LabProgram {
   public static int daysInFeb (int userYear) {
      int inputYear = userYear;
      double div400;
      double div4;
     
      
      div4 = inputYear % 4;
      div400 = inputYear % 400;
      
      if (div4 > 0) {
         return 28;
      } else {
         if (div400 > 0) {
            div4 = div400 % 4.0;
            if ((div400 == 100) || (div400 == 200) || (div400 == 300)){
               return 28;
            } else if (div4 > 0) {
               return 28;
            } else {
               return 29;
            }
         } else {
            return 29;
         }
      }
      }
     public static void main ( String[] args) {
      Scanner scnr = new Scanner(System.in);
      int userYear;
      userYear = scnr.nextInt();
      System.out.println(userYear + " has " + daysInFeb(userYear) + " days in February.");
  }
  }
