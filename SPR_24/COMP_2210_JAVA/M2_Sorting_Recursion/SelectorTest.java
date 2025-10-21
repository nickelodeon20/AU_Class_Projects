import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Collection;
import java.util.Comparator;
import java.util.Arrays;


public class SelectorTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }
   
   @Test public void test1() {
      Collection<Integer> coll = Arrays.asList(1, 2, 3, 4, 5);
      int k = 2;
      Comparator<Integer> comp = ascendingInteger;
      Integer expected = 7;
      Integer actual = Selector.kmax(coll, 2, comp);
      Assert.assertEquals(expected, actual);
   }

   /** A test that always fails. **/
   @Test public void defaultTest() {
      Assert.assertEquals("Default test added by jGRASP. Delete "
            + "this test once you have added your own.", 0, 1);
   }
// method in generics
   static Comparator<Integer> ascendingInteger =
        new Comparator<Integer>() {
           public int compare(Integer i1, Integer i2) {
              return i1.compareTo(i2);
           }
        };
}
