import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class SearchTest1Test {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }
   
   @Test public void test_1() {
      int[] array = {5, 9, 3, 1, 7}; 
      int target = 1;
      int expected = 3;
      int actual = Search.search(array, target);
      assertEquals(expected, actual);
   }
   


}
