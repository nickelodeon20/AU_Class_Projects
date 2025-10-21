import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.text.html.HTMLDocument.Iterator;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Nick Schroeder (nls0030@auburn.edu)
 */
public class DoubletsTreeSet implements WordLadderGame {

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   /////////////////////////////////////////////////////////////////////////////
   // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
   // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
   // PURPOSE OF YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
   // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
   // table with chaining).                                                   //
   /////////////////////////////////////////////////////////////////////////////

   // Set vs. SortedSet vs. TreeSet //
   
   // Set:
   // - Root interface collection in JCF, representing collection w/o duplicates

   // SortedSet:
   // - Child of Set, maintains elements in Sorted Order (natural or comparator order)

   // TreeSet:
   // - Class that implements SortedSet interface
   // - Stores elements in sorted Tree structure (specifically Red-Black tree)
   // - Guarantees elements sorted by natural/comparator order

   // final - cannot change vairable values or "reset" once assigned 
   private final TreeSet<String> lexicon;
   private List<String> MiniLad = new ArrayList<String>();
   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    */
   public DoubletsTreeSet(InputStream in) {
      lexicon = new TreeSet<> (String.CASE_INSENSITIVE_ORDER);
      try {
         //////////////////////////////////////
         // INSTANTIATE lexicon OBJECT HERE  //
         //////////////////////////////////////
         // intial capacity = 16 (default)
         Scanner s = new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.nextLine();
            int i = str.indexOf(' ');
            str = i >= 0 ? str.substring(0, i) : str;
            lexicon.add(str.toLowerCase());
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }


   //////////////////////////////////////////////////////////////
   // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
   //////////////////////////////////////////////////////////////
   /**
    * Returns the total number of words in the current lexicon.
    *
    * @return number of words in the lexicon
    */
   // Lexicon: TreeSet 
   // - TreeSet = type of Set/Collection
   public int getWordCount() {
      return lexicon.size();
   }

   /**
    * Checks to see if the given string is a word.
    *
    * @param  str the string to check
    * @return     true if str is a word, false otherwise
    */
   // Lexicon is our collection of "words"
   // check if given String is in Lexicon (valid words)
   public boolean isWord(String str) {
      return lexicon.contains(str.toLowerCase());
   }


   /**
    * Returns the Hamming distance between two strings, str1 and str2. The
    * Hamming distance between two strings of equal length is defined as the
    * number of positions at which the corresponding symbols are different. The
    * Hamming distance is undefined if the strings have different length, and
    * this method returns -1 in that case. See the following link for
    * reference: https://en.wikipedia.org/wiki/Hamming_distance
    *
    * @param  str1 the first string
    * @param  str2 the second string
    * @return      the Hamming distance between str1 and str2 if they are the
    *                  same length, -1 otherwise
    */
    // # of different letters between two strings
   public int getHammingDistance(String str1, String str2) {
      if (str1 == null || str2 == null) {
         return -1;
      }
      
      // different lengths = no hamming distance
      if (str1.length() != str2.length()) {
         return -1;
      }
      str1 = str1.toLowerCase();
      str2 = str2.toLowerCase();

      int distance = 0;
      // iterate through length of String, & compare each letter
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            distance++;
         }
      }
      // construct neighbors first? - getNeighbors(str1) / getNeighbors(str2)
        // compare str1[] to str2[]? --> add most similar ones to new array sim[]
        //      repeat above process until word{str1}.equals(word{str2})
        //      - recursion?           check ^^ before recursive call
      return distance;
   }


   /**
    * Returns all the words that have a Hamming distance of one relative to the
    * given word.
    *
    * @param  word the given word
    * @return      the neighbors of the given word
    */
   public List<String> getNeighbors(String word) {

      List<String> neighbors = new ArrayList<String>();
      
      ///// IDEA CODE - maybe??? /////
      int i = 0;
      int j = 0;

      for (i = 0; i < word.length(); i ++) {
        for (j = 0; j < 26; j++) {
            String newWord = buildWord(word, i, j);
            if (newWord.length() == 0) {
               continue;
            } else if (newWord.equals(word)) {
               continue;
            } else {
               neighbors.add(newWord);
            }
        } 
      }
      // change 1 letter at a time - entire alphabet
      return neighbors;
   }


   /**
    * Checks to see if the given sequence of strings is a valid word ladder.
    *
    * @param  sequence the given sequence of strings
    * @return          true if the given sequence is a valid word ladder,
    *                       false otherwise
    */
   // 1. Special Cases - Outliers
   // - Sequence is null --> object does not exist
   // - Sequence is empty --> object exists but size is 0

   // requirements of WordLadder, features of WordLadder
   // - Adjacent Hamming Distance MUST == 1
   // - Words MUST BE in Lexicon (valid word)
   @Override
   public boolean isWordLadder(List<String> sequence) {
      if (sequence.isEmpty() || sequence == null) {
         return false;
      }
      // getHammingDistance == 1
      for (int i = 0; i < sequence.size()-1; i++) {
         // check for valid words
         if (!isWord(sequence.get(i)) || !isWord(sequence.get(i+1))) {
            return false;
         } // check for hamming distnace == 1
         if (getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1) {
            return false;
         }
      }
      return true;
   }


  /**
  * Returns a minimum-length word ladder from start to end. If multiple
  * minimum-length word ladders exist, no guarantee is made regarding which
  * one is returned. If no word ladder exists, this method returns an empty
  * list.
  *
  * Breadth-first search must be used in all implementing classes.
  *
  * @param  start  the starting word
  * @param  end    the ending word
  * @return        a minimum length word ladder from start to end
  */
  // 1) check valid words
  // 2) for start word & end word
  //    - getNeighbors
  //    - see if neighbors equal each other
  public List<String> getMinLadder(String start, String end) {
      if (start == null || !isWord(start)) {
         return MiniLad;
      }
      if (end == null || !isWord(end)) {
         return MiniLad;
      }
      // check for special case
      if (end.equals(start)) {
         MiniLad.add(start);
         return MiniLad;
      }
      // Initialize empty list
      List<String> startNeighbors = getNeighbors(start);
      List<String> endNeighbors = getNeighbors(end);
      int i = 0;
      
      for (String word : startNeighbors) {
         if (endNeighbors.contains(word)) {
            MiniLad.add(start); 
            MiniLad.add(word);
            MiniLad.add(end);
            return MiniLad;
         }
         for (i = 0; i < endNeighbors.size(); i++) {
            if (getHammingDistance(word, endNeighbors.get(i)) == 1) {
               MiniLad.add(start);
               getMinLadder(word, endNeighbors.get(i));
            }
         }
      }
      return MiniLad;
   }

   //////////////////////////////////////////////////
   /// private methods ///
   //////////////////////////////////////////////////
         
   // takes String values from Lexicon, gives numerical value


   
    // swaps letter into word - maybe use, probably need to change some
    private String buildWord(String word, int WordI, int ArrayI) {
       char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
             'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
       String newString = "";
       // find letter in word - String -> char[]
       // replace letter with new letter
       // changed char[] -> String - return string
       char[] wordLet = word.toCharArray();
       wordLet[WordI] = alphabet[ArrayI];
       for (char i : wordLet) {
          newString += i; // FIXED
       }
       if (isWord(newString)) {
          return newString;
       }
       return "";
    }
    
}
