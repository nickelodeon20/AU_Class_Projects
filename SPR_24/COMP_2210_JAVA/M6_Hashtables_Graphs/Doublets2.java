

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Provides an implementation of the WordLadderGame interface.
 */

 /*confused, but just give the code */
public class Doublets2 implements WordLadderGame {
// the word list used to validate words                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
   private final TreeSet<String> lexicon;

   public Doublets2(InputStream in) {
      //instantiate lexicon object here
      lexicon = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
      try {
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            //// INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
            String str = s.nextLine();
            int i = str.indexOf(' ');
            str = i >= 0 ? str.substring(0, i) : str;
            lexicon.add(str.toLowerCase());
         }
         in.close();
      } catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }

   /**
    * Returns the total number of words in the current lexicon.
    *
    * @return number of words in the lexicon*/
   @Override
   public int getWordCount() {
      //built-in function for treeset
      return lexicon.size();
   }
  /**
    * Checks to see if the given string is a word.
    *
    * @param  str the string to check
    * @return     true if str is a word, false otherwise
    */
   @Override
   public boolean isWord(String str) {
      return lexicon.contains(str);
   }

   /**
    * Returns the Hamming distance between two strings, str1 and str2. The
    * Hamming distance between two strings of 1.equal length is defined as the
    * 2.number of positions at which the corresponding symbols are different. 3.The
    * Hamming distance is undefined if the strings have different length, and
    * this method returns -1 in that case. See the following link for
    * reference: https://en.wikipedia.org/wiki/Hamming_distance
    *
    * @param  str1 the first string
    * @param  str2 the second string
    * @return      the Hamming distance between str1 and str2 if they are the
    *                  same length, -1 otherwise
    */
   @Override
   public int getHammingDistance(String str1, String str2) {
      //for scenario No.3
      if (str1 == null || str2 == null || str1.length() != str2.length()) {
         return -1;
      }
      int dist = 0;
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            dist++;
         }
      }
      return dist;
   }

   /**
    * Checks to see if the given sequence of strings is a valid word ladder.
    *
    * @param  sequence the given sequence of strings
    * @return          true if the given sequence is a valid word ladder,
    *                       false otherwise
    */
   /*I am confused for this one, maybe cover for next week */
   @Override
   public boolean isWordLadder(List<String> sequence) {
      if (sequence == null || sequence.isEmpty()) {
         return false;
      }
      for (int i = 0; i < sequence.size(); i++) {
         if (!isWord(sequence.get(i))) {
            return false;
         }
         if (i > 0 && getHammingDistance(sequence.get(i - 1), sequence.get(i)) != 1) {
            return false;
         }
      }
      return true;
   }
   /**
    * Returns all the words that have a Hamming distance of one relative: means return hamming distance =1
    * , also means we only have one letter is different between neight and given word, and also we need makesure 
    * the neighor word is a valid word in the lexicon.  to the
    * given word.
    *
    * @param  word the given word
    * @return      the neighbors of the given word
    */
   @Override
   public List<String> getNeighbors(String word) {
      if (word == null || word.isEmpty()) {
         return Collections.emptyList();
      }
      word = word.toLowerCase();
      //neighbors list contains all the valid neighbor for this given word
      ArrayList<String> neighbors = new ArrayList<>();
   
      for (int i = 0; i < word.length(); i++) {
         // example of substring
         /*public class SubstringExample{  
      public static void main(String args[]){  
      String s1="javatpoint";  
      start index is inclusive, the end index is exclusive, if the
      end index didn't indicate, and then return all the charaters from the start to end
      System.out.println(s1.substring(2,4));//returns va  
      System.out.println(s1.substring(2));//returns vatpoint  
      }}   */
      /*cut the word into partBi and partAi */
         String partBi = word.substring(0, i);
         /*a is first part of word, b is the left part of word */
         String partAi = word.substring(i + 1);
         //change the letter at location i into c, and then check whe
         for (char c = 'a'; c <= 'z'; c++) {
            if (c == word.charAt(i)) {
               continue;
            }
            String newWord = partBi + c + partAi;
            if (isWord(newWord)) {
               neighbors.add(newWord);
            }
         }
      }
   
      return neighbors;
   }

   @Override
   public List<String> getMinLadder(String start, String end) {
      if (start == null || end == null
         || start.isEmpty() || end.isEmpty()
         || start.length() != end.length()) {
         return Collections.emptyList();
      }
      if (start.equals(end)) {
         return Collections.singletonList(start);
      }
      start = start.toLowerCase();
      end = end.toLowerCase();
      ArrayDeque<Node> queue = new ArrayDeque<>();
      TreeSet<String> seen = new TreeSet<>();
   
      queue.add(new Node(start, null));
      while (!queue.isEmpty()) {
         Node w = queue.removeFirst();
         if (seen.contains(w.getWord())) {
            continue;
         }
         seen.add(w.getWord());
      
         List<String> neighbors = getNeighbors(w.getWord());
         for (String i : neighbors) {
            if (seen.contains(i)) {
               continue;
            }
            if (i.equals(end)) {
               return new Node(i, w).toPath();
            }
            queue.add(new Node(i, w));
         }
      }
      return Collections.emptyList();
   }

   private static class Node {
      private final String word;
      private final Node parent;
   
      private Node(String word, Node parent) {
         this.word = word;
         this.parent = parent;
      }
   
      public String getWord() {
         return word;
      }
   
      public List<String> toPath() {
         ArrayList<String> list = new ArrayList<>();
         Node n = this;
         while (n != null) {
            list.add(n.word);
            n = n.parent;
         }
         Collections.reverse(list);
         return list;
      }
   }
}

