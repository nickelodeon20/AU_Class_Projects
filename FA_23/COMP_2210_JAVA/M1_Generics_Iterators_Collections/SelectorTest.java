import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class SelectorTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }
   
   @Test public void Kmin1Test() {
      int[] a = {3, 5, 2, 7};
      int k = 4;
      int expected = 7;
      int actual = Selector.kmin(a, k);
      assertEquals(expected, actual);
   }
   
   @Test public void Kmin3Test() {
      int[] a = {1, 3, 5, 7, 9};
      int k = 5;
      int expected = 1;
      int actual = Selector.kmin(a, k);
      assertEquals(expected, actual);
   }

}
