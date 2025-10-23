import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class Game1Test {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void defaultTest() {
      String[] n = ["T", "I", "G", "E", "R"];
      setBoard(n);
      loadLexicon(wordfiles/words_medium.txt);
      int Min = 3;
   }
}
