import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class LinkedSetTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }
   
   /*@Test public void AddRemoveTest() 
   {
      Set co = new LinkedSet();
      boolean t = co.contains(4); 
      t = co.add(4); 
      t = co.contains(4); 
      t = co.add(1); 
      t = co.add(5); 
      t = co.add(3); 
      t = co.add(1); 
      t = co.add(2); 
      t = co.contains(3); 
      t = co.remove(4); 
      t = co.contains(4); 
      t = co.remove(1); 
      t = co.remove(5);
      t = co.contains(5);
      
      //Assert.assertEquals("Default test added by jGRASP. Delete "
         //   + "this test once you have added your own.", 0, 1);
   }*/
   @Test public void ComplementTest() {
      Set test = new LinkedSet();
      test.add(1);
      test.add(2);
      test.add(3);
   
      Set actual = test.complement(test);
      boolean t;
      
      if (actual.size() == 0) {
         t = true;
      } else {
         t = false;
      }
   }
}
