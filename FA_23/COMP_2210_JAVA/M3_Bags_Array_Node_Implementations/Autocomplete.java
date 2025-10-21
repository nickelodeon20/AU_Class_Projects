import java.util.Arrays;


/**
 * Autocomplete.
 */
public class Autocomplete {
	// Array can only hold type "Term"; attribute
   private Term[] terms;

	/**
	 * Initializes a data structure from the given array of terms.
	 * This method throws a NullPointerException if terms is null.
	 */
   public Autocomplete(Term[] terms) {
      if (terms == null) {
         throw new NullPointerException();
      }
   	// LEFT: this.terms --> attribute, private "terms"
   	// RIGHT terms --> input parameter, instance variable
      Arrays.sort(terms, Term::compareTo);
      this.terms = terms;
   }

	/** 
	 * Returns all terms that start with the given prefix, in descending order of weight. 
	 * This method throws a NullPointerException if prefix is null.
	 */
   public Term[] allMatches(String prefix) {
      if (prefix == null) {
         throw new NullPointerException();
      }
      if (prefix.isEmpty()) {
         Term[] a = Arrays.copyOf(terms, terms.length);
         Arrays.sort(a, Term.byDescendingWeightOrder());
         return a;
      }
   	// find all term objects with given prefix; store in array "found"
      int first = BinarySearch.firstIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
      if (first < 0 || first >= terms.length) {
         return new Term[0];
      }
      int last = BinarySearch.lastIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
      
      Term[] found = Arrays.copyOfRange(terms, first, last + 1);
      Arrays.sort(found, Term.byDescendingWeightOrder());
      return found;
   }

}
