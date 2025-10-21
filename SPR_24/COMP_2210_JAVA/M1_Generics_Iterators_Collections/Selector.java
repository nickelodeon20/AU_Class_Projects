import java.util.Arrays;
import java.util.Collections;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Nicholas Schroeder (nls0030@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  TODAY
*
*/
public final class Selector {

    /**
     * Can't instantiate this class.
     *
     * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
     *
     */
   private Selector() { }


    /**
     * Selects the minimum value from the array a. This method
     * throws IllegalArgumentException if a is null or has zero
     * length. The array a is not changed by this method.
     */
   public static int min(int[] a) {
      if (a == null) {
         throw new IllegalArgumentException("Input array is null");
      }
      else if (a.length == 0) {
         throw new IllegalArgumentException("Input array is empty");
      }
      int i;
      int min = a[0];
         
      for (i = 1; i < a.length; i++) {
         if (a[i] < min) {
            min = a[i];
         }
      }
        
      return min;
   }


    /**
     * Selects the maximum value from the array a. This method
     * throws IllegalArgumentException if a is null or has zero
     * length. The array a is not changed by this method.
     */
   public static int max(int[] a) {
      if (a == null) {
         throw new IllegalArgumentException("Input array is null.");
      }
      else if (a.length == 0) {
         throw new IllegalArgumentException("Input array is empty.");
      }
      int i;
      int max = a[0];
         
      for (i = 1; i < a.length; i++) {
         if (a[i] > max) {
            max = a[i];
         }
      }
        
      return max;
   }


    /**
     * Selects the kth minimum value from the array a. This method
     * throws IllegalArgumentException if a is null, has zero length,
     * or if there is no kth minimum value. Note that there is no kth
     * minimum value if k < 1, k > a.length, or if k is larger than
     * the number of distinct values in the array. The array a is not
     * changed by this method.
     */
   public static int kmin(int[] a, int k) {
      if (a == null) {
         throw new IllegalArgumentException("Input array is null.");
      }
      else if (a.length == 0) {
         throw new IllegalArgumentException("Input array is empty.");
      }
      else if ((k > a.length) || (k < 1)) {
         throw new IllegalArgumentException("K Value is invalid.");
      }
      
      int temp[] = {};
      for (int x: a) {
         int[] newTemp = Arrays.copyOf(temp, temp.length + 1);
         newTemp[temp.length] = x;
         temp = newTemp;
         // finally copy array "a" into temp without changing "a"
      }
      // sort temp in increasing order
      Arrays.sort(temp);
      
      int count = 1;
      int arr[] = {temp[0]};
      while (count < k) {
         for (int i = 1; i < temp.length; i++) {
            if (temp[i] != temp[i-1]) {
               int[] newArr = Arrays.copyOf(arr, arr.length + 1);
               newArr[arr.length] = temp[i];
               arr = newArr;
               count++;
            }
         } //those will help us retrieve the distinguished elements 
      }    // from "temp" and assign to "arr"
      if (count < k) {
         throw new IllegalArgumentException("K Value is invalid.");
      }
      
      return arr[k-1];
   }


    /**
     * Selects the kth maximum value from the array a. This method
     * throws IllegalArgumentException if a is null, has zero length,
     * or if there is no kth maximum value. Note that there is no kth
     * maximum value if k < 1, k > a.length, or if k is larger than
     * the number of distinct values in the array. The array a is not
     * changed by this method.
     */
   public static int kmax(int[] a, int k) {
      if (a == null) {
         throw new IllegalArgumentException("Input array is null.");
      }
      else if (a.length == 0) {
         throw new IllegalArgumentException("Input array is empty.");
      }
      else if ((k > a.length) || (k < 1)) {
         throw new IllegalArgumentException("K Value is invalid.");
      }
      // 1) copy array 'a' into temp
      int[] temp = {};
      
      for (int x : a) {
         int[] newTemp = Arrays.copyOf(temp, temp.length + 1);
         newTemp[temp.length] = x;
         temp = newTemp;
      }
      // 2) sort temp by increasing order
      Arrays.sort(temp);
      
      
      int count = 1;
      int arr[] = {temp[temp.length-1]};
      
      while (count < k) {
         for (int i = temp.length-2; i >= 1; i--) {
            if (temp[i] != temp[i+1]) {
               int[] newArr = Arrays.copyOf(arr, arr.length + 1);
               newArr[arr.length] = temp[i];
               arr = newArr;
               count++;
            } // Count: # of distinct elements
         } // Arr: temp but in decreasing order w/o duplicates
      }
      if (count < k) {
         throw new IllegalArgumentException("K Value is invalid.");
      }
      
      return arr[k - 1];
   }


    /**
     * Returns an array containing all the values in a in the
     * range [low..high]; that is, all the values that are greater
     * than or equal to low and less than or equal to high,
     * including duplicate values. The length of the returned array
     * is the same as the number of values in the range [low..high].
     * If there are no qualifying values, this method returns a
     * zero-length array. Note that low and high do not have
     * to be actual values in a. This method throws an
     * IllegalArgumentException if a is null or has zero length.
     * The array a is not changed by this method.
     */
   public static int[] range(int[] a, int low, int high) {
      if (a == null) {
         throw new IllegalArgumentException("Array is null");
      } else if (a.length == 0) {
         throw new IllegalArgumentException("Array length is 0");
      }
      
      int[] temp = {};
      for (int x : a) {
         int[] newTemp = Arrays.copyOf(temp, temp.length + 1);
         newTemp[temp.length] = x;
         temp = newTemp;    
      }
      
      
      Arrays.sort(temp);
      
      int i;
      int[] rangeArr = {};
      for (i = 0; i < temp.length; i++) {
         if ((temp[i] >= low) && (temp[i] <= high)) {
            int[] newArr = Arrays.copyOf(rangeArr, rangeArr.length +1);
            newArr[rangeArr.length] = temp[i];
            rangeArr = newArr;
         }
      }
      return rangeArr;
   }


    /**
     * Returns the smallest value in a that is greater than or equal to
     * the given key. This method throws an IllegalArgumentException if
     * a is null or has zero length, or if there is no qualifying
     * value. Note that key does not have to be an actual value in a.
     * The array a is not changed by this method.
     */
   public static int ceiling(int[] a, int key) {
      if (a == null) {
         throw new IllegalArgumentException("Input array is null.");
      }
      else if (a.length == 0) {
         throw new IllegalArgumentException("Input array is empty.");
      }
   
      int[] temp = {};
      for(int x : a){
         int[] keyRange = Arrays.copyOf(temp, temp.length+1);
         if (x >= key) {
            keyRange[temp.length] = x;
            temp = keyRange;
         }
      }
      
      return min(temp);
   }


    /**
     * Returns the largest value in a that is less than or equal to
     * the given key. This method throws an IllegalArgumentException if
     * a is null or has zero length, or if there is no qualifying
     * value. Note that key does not have to be an actual value in a.
     * The array a is not changed by this method.
     */
   public static int floor(int[] a, int key) {
      if (a == null) {
         throw new IllegalArgumentException("Input array is null.");
      }
      else if (a.length == 0) {
         throw new IllegalArgumentException("Input array is empty.");
      }
      
      int[] temp = {};
      for(int x : a){
         int[] keyRange = Arrays.copyOf(temp, temp.length+1);
         if (x <= key) {
            keyRange[temp.length] = x;
            temp = keyRange;
         }
      }
      
      
      if(temp.length <= 0) {
         throw new IllegalArgumentException("Key is invalid");
      }
      
      return max(temp);
   }

}
