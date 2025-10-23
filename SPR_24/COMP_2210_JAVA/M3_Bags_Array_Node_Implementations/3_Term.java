import java.util.Comparator;

/**
 * Autocomplete term representing a (query, weight) pair.
 * 
 */
public class Term implements Comparable<Term> {
   // Comparable
   // 1. Can only be used to define natural order of objects (numbers, string)
   // 2. One method - compareTo(T obj) -> either <0, =0, >0
   // 3. Class implement Comparable Interface, objects will be in natural order

   // Comparator
   // 1. Interface used to define custom comparison logic
   //     -- separate from natural ordering of objects
   // 2. One method - comp.compare(T obj, T obj2) -> either <0, =0, >0

   /**
    * Initialize a term with the given query and weight.
    * This method throws a NullPointerException if query is null,
    * and an IllegalArgumentException if weight is negative.
    */
   private String query; 
   private long weight;
    
   // Constructor for Term class
   public Term(String query, long weight) {
      if (query == null) {
         throw new NullPointerException();
      }
      if (weight < 0) {
         throw new IllegalArgumentException();
      }
      // ".this" keyword differentiates parameters from attributes 
      this.query = query;
      this.weight = weight;
   }

   /**
    * Compares the two terms in descending order of weight.
    */

   // Method returns Comparator object of type Term
   public static Comparator<Term> byDescendingWeightOrder() {
      return new Comparator<Term>() {
         // instantiates Comparator interface to compare two values
            public int compare(Term t1, Term t2) {
               return Long.compare(t2.weight, t1.weight);
            }
         };
   }

   /**
    * Compares the two terms in ascending lexicographic order of query,
    * but using only the first length characters of query. This method
    * throws an IllegalArgumentException if length is less than or equal
    * to zero.
    */

   // Method returns Comparator object of type Term
   // - Uses "length" int parameter to take prefix, then order Term objects w/ Comparator
   public static Comparator<Term> byPrefixOrder(int length) {
      if (length <= 0) {
         throw new IllegalArgumentException();
      }
      return 
         new Comparator<Term>() {
            public int compare(Term a, Term b) {
               String aSub = a.query.substring(0, Math.min(a.query.length(), length));
               String bSub = b.query.substring(0, Math.min(b.query.length(), length));
               return aSub.compareTo(bSub);
            }
         };
   }

   /**
    * Compares this term with the other term in ascending lexicographic order
    * of query.
    */
   @Override
   public int compareTo(Term other) {
      return query.compareTo(other.query);
   }

   /**
    * Returns a string representation of this term in the following format:
    * query followed by a tab followed by weight
    */
   @Override
   public String toString() {
      return String.format("%s\t%d", query, weight);
      //return query + "    " + weight;
   }

}
