import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author Nicholas Schroeder (nls0030@auburn.edu)
 *
 */
// A extends B --> A is subclass, B is superclass
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

   //////////////////////////////////////////////////////////
   // Do not change the following three fields in any way. //
   //////////////////////////////////////////////////////////

   /** References to the first and last node of the list. */
   Node front;
   Node rear;

   /** The number of nodes in the list. */
   int size;

   /////////////////////////////////////////////////////////
   // Do not change the following constructor in any way. //
   /////////////////////////////////////////////////////////

   /**
    * Instantiates an empty LinkedSet.
    */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


   //////////////////////////////////////////////////
   // Public interface and class-specific methods. //
   //////////////////////////////////////////////////

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this LinkedSet.
    *
    * @return a string representation of this LinkedSet
    */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }


   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements, false otherwise.
    */
   public boolean isEmpty() {
      return (size == 0);
   }

   
   public Node current = front;

   /**
    * Ensures the collection contains the specified element. Neither duplicate
    * nor null values are allowed. This method ensures that the elements in the
    * linked list are maintained in ascending natural order.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   public boolean add(T element) {
      if (element == null){
         return false;
      }
      Node n = new Node(element);
      Node temp = front;
   
      if (isEmpty()) {
         front = n;
         rear = n;
      } else {
         // 1) when temp value < current value
         while ((temp != null) && (temp.element.compareTo(element) < 0)) {
            temp = temp.next;
         }
         // 2) when Temp >> all values in LinkedSet
         if (temp == null) {
            n.prev = rear;
            rear.next = n;
            rear = n;
         // 3) Cannot add duplicates
         } else if (temp.element.compareTo(element) == 0) {
            return false;
         // 4) add in front
         } else if (temp == front) {
            n.next = front;
            front.prev = n;
            front = n;
         // 5) Temp somewhere in middle
         } else {
            n.prev = temp.prev;
            n.next = temp;
            temp.prev.next = n;
            temp.prev = n;
         }
      }
      size++;
      return true;
   }

   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. This method, consistent with add, ensures
    * that the elements in the linked lists are maintained in ascending
    * natural order.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   public boolean remove(T element) {
      if (element == null) {
         return false;
      }
      Node temp = locate(element);
      // if element in LinkedSet, then search
   
      // did not find element in list, reached end of list
      if (temp == null) {
         return false;
      }
      // removing only node in list
      if (size == 1) {
         front = null;
         rear = null;
         size = 0;
         return true;
      }
      // removing first node
      if (temp == front) {
         front = front.next;
         front.prev = null;
      }
      // removing any node BUT the first node
      else {
         // Last Node
         if (temp.next == null) {
            rear = temp.prev;
            temp.prev.next = null;
            size--;
            return true;
         }
         // Middle nodes
         temp.prev.next = temp.next;
         temp.next.prev = temp.prev;
      }
      size--;
      return true; 
   }


   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
   public boolean contains(T element) {
      if (size == 0 || element == null) {
         return false;
      }
      return locate(element) != null;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(Set<T> s) {
      // Set cannot have duplicates; may not be in order though
      if (s == null) {
         return false;
      } 
      if (size() != s.size()) {
         return false;
      }
      // next() comes from iterator() interface
      Iterator<T> itr = iterator();
      boolean result = true;
      while (result && itr.hasNext()) {
         // A &= B --> A = A&B
         result &= s.contains(itr.next());
      }
      
      
      return false;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(LinkedSet<T> s) {
      if (s == null) {
         return false;
      }
      if (size != s.size()) {
         return false;
      }
      boolean result = true;
      // iterator for current LinkedSet
      Iterator<T> i = iterator();
      // iterator for 's'
      Iterator<T> j = iterator();
   
      while (result && i.hasNext()) {
         // next() - retrieves elements
         T first = i.next();
         T second = j.next();
         if (first.compareTo(second) == 0) {
            result = true;
         } else {
            result = false;
            return result;
         }
      }
      
      return result;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(Set<T> s){
      // No order in sets
      Set<T> un = new LinkedSet<T>();
      Iterator<T> itr = iterator();
   
      while (itr.hasNext()) {
         un.add(itr.next());
      }
      itr = s.iterator();
      while(itr.hasNext()) {
         un.add(itr.next());
      }
      return un;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(LinkedSet<T> s){
      // LinkedSet has natural order
      LinkedSet<T> un = new LinkedSet<T>();
      int cmp;
      Node i = front; // front of current set
      Node j = s.front; // front of parameter set
   
      while (i != null && j != null) {
         cmp = i.element.compareTo(j.element);
      
         if (cmp == 0) {
            un.push(i.element);
            i = i.next;
            j = j.next;
         } else if (cmp < 0) {
            un.push(i.element);
            i = i.next;
         } else {
            un.push(j.element);
            j = j.next;
         }
      }
      if (i == null) {
         while (j != null) {
            un.push(j.element);
            j = j.next;
         }
      }
      if (j == null) {
         while (j != null) {
            un.push(j.element);
            j = j.next;
         }
      }
      
      return un;
   }


   /**
    * Returns a set that is the intersection of this set and the parameter set.
    *
    * @return  a set that contains elements that are in both this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      if (s == null) {
         throw new NoSuchElementException();
      }
      if (equals(s)) {
         return s;
      }
      Iterator<T> itr = iterator();
      Set<T> un = new LinkedSet<T>();
   
      while (itr.hasNext()) {
         T element = itr.next();
         if (s.contains(element)) {
            un.add(element);
         
         }
      }
      // use iterator(Y), hasNext(Y), next(Y), contains(Y) from S
      // then add (Y)
      return un;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(LinkedSet<T> s) {
      // use cmp, 2 front nodes, for s & current Linked set
      // similar w/ second Union code
      // LinkedSet has natural order
      LinkedSet<T> un = new LinkedSet<T>();
      int cmp;
      Node i = front; // front of current set
      Node j = s.front; // front of parameter set
   
      while (i != null && j != null) {
         cmp = i.element.compareTo(j.element);
      
         if (cmp == 0) {
            un.push(i.element);
            i = i.next;
            j = j.next;
         } else if (cmp < 0) {
            un.push(i.element);
            i = i.next;
         } else {
            un.push(j.element);
            j = j.next;
         }
      }
      if (i == null) {
         while (j != null) {
            un.push(j.element);
            j = j.next;
         }
      }
      if (j == null) {
         while (j != null) {
            un.push(j.element);
            j = j.next;
         }
      } 
      return un;
   }


   /**
    * Returns a set that is the complement of this set and the parameter set.
    *
    * @return  a set that contains elements that are in this set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      // LinkedSet co = new LinkedSet<T>();
      // iterator, hasNext, next, contains from 's', add, next attribute
      Iterator<T> itr = iterator();
      Set<T> co = new LinkedSet<T>();
   
      if (equals(s)) {
         return co;
      }
      while (itr.hasNext()) {
         T element = itr.next();
         if (!s.contains(element) && this.contains(element)) {
            co.add(element);
         }
      }
      // use iterator(Y), hasNext(Y), next(Y), contains(Y) from S
      // then add (Y)
      return co;
   }


   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(LinkedSet<T> s) {
      // cmp
      // get 2 front nodes for each LinkedSet
      // compareTo
      // next, push, element, while loop
      if (size == s.size() && size == 0) {
         return s;
      }
      LinkedSet<T> pair = new LinkedSet<T>();
      boolean found;
      Node i = front; // front of current set
      Node j = s.front; // front of parameter set
   
      while (i != null) {
         // "this" set
         T val1 = i.element;
         while (j != null) {
            // parameter set
            T val2 = j.element;
            found = this.contains(val2);
         
            if (!found) {
               pair.push(val1);  
            }
            j = j.next;
            i = i.next;
         }
      }
      if (j == null) {
         while (i != null) {
            pair.push(i.element);
            i = i.next;
         }
      } 
      return pair;
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in ascending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> iterator() {
      // i < size??
      return new AscendingLinkedSetIterator();
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in descending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> descendingIterator() {
      return new DescendingLinkedSetIterator();
   }


   /**
    * Returns an iterator over the members of the power set
    * of this LinkedSet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      return new PowerSetIterator();
   }



   //////////////////////////////
   // Private utility methods. //
   //////////////////////////////

   // Feel free to add as many private methods as you need.
   private Node locate (T element) {
      Node temp = front;
      // if element in LinkedSet, then search
      while (temp != null) {
         if (temp.element.compareTo(element) == 0) {
            return temp;
         }
         temp = temp.next;
      }
      return temp;
   }

   // add a new node after rear; special case of "add"
   private boolean push (T element) {
      Node n = new Node(element);
   
      if (size == 0){
         front = n;
         rear = n;
      } else {
         rear.next = n;
         n.prev = rear;
         rear = n;
      }
      size++;
      return true;
   }

   private boolean testBit (int n, int val) {
      if ((val & (1 << n)) != 0) {
         // 1 << n -> bitwise left shift operation in Java
         // this case, it shifts integer a left by "n" bits
      
         // 1 : 1 --> 00000001     returns 1 in decimal
         // 3 : 1 --> 00001000     returns 8 in decimal
      }
      return true;
   }
   ////////////////////
   // Nested classes //
   ////////////////////
   private class AscendingLinkedSetIterator implements Iterator<T> {
      private Node p;
      public AscendingLinkedSetIterator() {
         p = front;
      }
      @Override
      public boolean hasNext() {
         return p != null;
      }
      @Override
      public T next() {
         if (p == null) {
            throw new NoSuchElementException();
         }
         T element = p.element;
         p = p.next;
         return element;
         // element & an iterator
         // Walks through LinkedSet by force (FRONT --> BACK)
      }
   }
   private class DescendingLinkedSetIterator implements Iterator<T> {
      private Node p;
      public DescendingLinkedSetIterator() {
         p = rear;
      }
      @Override
      public boolean hasNext() {
         return p != null;
      }
      @Override
      public T next() {
         if (p == null) {
            throw new NoSuchElementException();
         }
         T element = p.element;
         p = p.prev;
         return element;
         // element & an iterator
         // Walks through LinkedSet by force (BACK --> FRONT)
      }
   }
   // iterator that goes over Sets of parameterized type (T)
   private class PowerSetIterator implements Iterator<Set<T>> {
      int max;
      int count;
   
      public PowerSetIterator() {
         count = 0;
         max = (int)Math.pow(2, size); // size = 3, max = 8
      }
      @Override
      public boolean hasNext() {
         return count < max;
      }
      @Override 
      public Set<T> next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         } 
         // create a list where element is a LinkedSet
         LinkedSet<T> list = new LinkedSet<T>();
         Iterator<T> itr = iterator();
         T next;
         int index = 0;
      
         while(itr.hasNext()) {
            next = itr.next();
            if (testBit(index, count)) {
               list.add(next);
               index++;
            }
         }
         count++;
         return list;
      }
   }
   //////////////////////////////////////////////
   // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
   //////////////////////////////////////////////

   /**
    * Defines a node class for a doubly-linked list.
    */
   class Node {
      /** the value stored in this node. */
      T element;
      /** a reference to the node after this node. */
      Node next;
      /** a reference to the node before this node. */
      Node prev;
   
      /**
       * Instantiate an empty node.
       */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }
   
      /**
       * Instantiate a node that containts element
       * and with no node before or after it.
       */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }

}
