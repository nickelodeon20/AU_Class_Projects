import java.util.Arrays;
import java.util.Comparator;

/**
 * Binary search.
 */
public class BinarySearch {

   /**
    * Returns the index of the first key in a[] that equals the search key, 
    * or -1 if no such key exists. This method throws a NullPointerException
    * if any parameter is null.
    */
   public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if ((a == null) || (key == null) || (comparator == null)) {
            throw new NullPointerException();
        }
        // sort input array
        Arrays.sort(a, comparator);

        int first = 0;
        int last = a.length-1;

        while (first < last) {
            int midIndex = (first + last)/2; // get middle index
            Key midElement = a[midIndex]; // get middle pointer
            int order = comparator.compare(midElement, key); // get <, >, = of element to Key
            // 5 4 3 3 2 2 2 1 
            if (order == 0) {
                // finds 1st occurence of element = key
                while ((midIndex >= 0) && (comparator.compare(a[midIndex], key) == 0)){
                    midIndex--;
                }
                return midIndex + 1;
            } else if (order < 0) {
                first = midIndex + 1;
            } else {
                last = midIndex - 1;
            }
        }
        return -1;
   }

   /**
    * Returns the index of the last key in a[] that equals the search key, 
    * or -1 if no such key exists. This method throws a NullPointerException
    * if any parameter is null.
    */
   public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if ((a == null) || (key == null) || (comparator == null)) {
            throw new NullPointerException();
        }
        Arrays.sort(a, comparator);


        int first = 0;
        int last = a.length - 1;

        while (first < last) {
            int midIndex = (first + last + 1)/2; // get middle index
            Key midElement = a[midIndex]; // get middle pointer
            int order = comparator.compare(midElement, key); // get <, >, = of element to Key
            // 5 4 3 3 2 2 2 1 
            if (order == 0) {
                // finds 1st occurence of element = key
                while ((midIndex >= 0) && (midIndex < a.length) && (comparator.compare(a[midIndex], key) == 0)){
                    midIndex++;
                }
                return midIndex - 1;
            } else if (order < 0) {
                first = midIndex + 1;
            } else {
                last = midIndex - 1;
            }
        }
        return -1;
   }

}