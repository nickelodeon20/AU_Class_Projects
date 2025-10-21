import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class MinOfThreeTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }
   @Test public void Test1A() {
      int a = 1;
      int b = 2;
      int c = 3;
      int expected = 1;
      int actual = MinOfThree.min2(a, b, c);
      assertEquals(expected, actual);
   
   }
   @Test public void Test2A() {
      int a = 1;
      int b = 1;
      int c = 3;
      int expected = 1;
      int actual = MinOfThree.min2(a, b, c);
   
      assertEquals(expected, actual);
   
   }
   @Test public void Test3A() {
      int a = 1;
      int b = 3;
      int c = 1;
      int expected = 1;
      int actual = MinOfThree.min2(a, b, c);
      assertEquals(expected, actual);
   
   }
   @Test public void Test1B() {
      int a = 3;
      int b = 1;
      int c = 1;
      int expected = 1;
      int actual = MinOfThree.min2(a, b, c);
      assertEquals(expected, actual);
   
   }
   @Test public void Test2B() {
      int a = 1;
      int b = 3;
      int c = 3;
      int expected = 1;
      int actual = MinOfThree.min2(a, b, c);
      assertEquals(expected, actual);
   
   }
   @Test public void Test3B() {
      int a = 3;
      int b = 1;
      int c = 3;
      int expected = 1;
      int actual = MinOfThree.min2(a, b, c);
      assertEquals(expected, actual);
   }
   
   @Test public void Test4A() {
      int a = 3;
      int b = 3;
      int c = 1;
      int expected = 1;
      int actual = MinOfThree.min2(a, b, c);
      assertEquals(expected, actual);
   }
   
   @Test public void Test4B() {
      int a = 1;
      int b = 1;
      int c = 1;
      int expected = 1;
      int actual = MinOfThree.min2(a, b, c);
      assertEquals(expected, actual);
   }
   
}
