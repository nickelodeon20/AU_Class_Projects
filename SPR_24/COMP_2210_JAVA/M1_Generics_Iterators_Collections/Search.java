public class Search {

   public static int search(int[] array, int target) {
      int i = 0;
      while ((array[i] != target) && (i < array.length)) {
         i++;
      }
      if (array[i] == target) {
         return i;
      } else {
         return -1;
      }
   }
   
   
}