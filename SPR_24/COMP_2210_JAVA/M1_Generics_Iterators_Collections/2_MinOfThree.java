/**
 * MinOfThree.java
 * Selects the minimum of three integer values. The logic of min1
 * has an error.
 */
public class MinOfThree {

   /**
    * Driver for the class.
    */
   public static void main(String[] args) {
      System.out.println(min1(3,1,2));
      System.out.println(min2(3,1,2));
   }

   /**
    * Returns the minimum values of its three parameters.
    *
    * @param a the first value
    * @param b the second value
    * @param c the third value
    * @return  the minimum of a, b, and c
    *
    */
   public static int min1(int a, int b, int c) {
      if ((a != b) && (a != c) && (b != c)) {
         if ((a < b) && (a < c)) {
            return a;
         }
         if ((b < a) && (b < c)) {
            return b;
         }
         return c;
      } else if ((a == b) || (a == c) || (b == c)) {
         if ((a == c) && (a < b)) {
            return a;
         } else if ((a == c) && (a > b)) {
            return b;
         } else if ((a == b) && (a < c)) {
            return a;
         } else if ((a == b) && (a > c)) {
            return c;
         } else if ((b == c) && (b < a)) {
            return b;
         } else if ((b == c) && (b > a)) {
            return a;
         } else {
            return a;
         }
      } else {
         return a;
      }
   }

   /**
    * Returns the minimum values of its three parameters.
    *
    * @param a the first value
    * @param b the second value
    * @param c the third value
    * @return  the minimum of a, b, and c
    *
    */
   public static int min2(int a, int b, int c) {
      if (a < b) {
         if (a < c) {
            return a;
         } 
         else if (c < a) {
            return c;
         } 
         else {
            return a;
         }
      } 
      else {
         if (b < c) {
            return b;
         } 
         else if (c < b) {
            return c;
         } 
         else {
            return b;
         }
      }
   }
}
