import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

public class SearchTest1 {
   
   @Test
   public void searchTest1() {
      int[] array = {5, 9, 3, 1, 7}; 
      int target = 1;
      int expected = 3;
      int actual = Search.search(array, target);
      assertEquals(expected, actual);
   }
   
}