import java.util.Iterator;

public Interface Bag<T> extends Interable<T> {
   boolean add(T element);
   boolean remove(T element);
   boolean contains(T element);
   int size();
   boolean isEmpty();
   Iterator<T> iterator();
}

public class ArrayBag<T> implements Bag<T> {
   private T[] elements; // <-- provide physical storage 
                         // choose appropriate data type for storing
   private int size;
   // convenience field
   
   // Constructer method
   public ArrayBag() {
      // size = 0 (empty); allocate SOME memory     
      Bag bag = new ArrayBag();
      // this references default constructor
      this(DEFAULT_CAPACITY);
   } 
   public ArrayBag(int capacity) {
   elements = (T[]) new Object[capcity];
   size = 0;
   }
    
   
   // implement interface methods above in ArrayBag
   // left-justfied: always adds left -> right
   public boolean add(T element) {
   // size = index of next value; FAILS when array maxes values
   //   - causes ArrayIndexOutOfBoundsException 
   if (size == elements.length) {
   return false;
   }
   elements[size] = element; 
   size++;
   return true;
   }  
   public boolean remove(T element) {
      int i = locate(element);
      // if it gets to the last element
      if (i < 0) {
      return false;
      }
      elements[i] = elements[--size];
      elements[size] = null;
      return true;
   }
   public boolean contains(T element) {
       for (int i = 0; i < size; i++) { 
         if (elements[i].equals(element)) {
            return true;
         }
       }
   }
   public int size() {
   return size
   }
   public boolean isEmpty() {
   reutrn size == 0;
   }
   public Iterator<T> iterator() {}
}


public class LinkedBag<T> implements Bag<T> {
   //create first value "front", equals null
   private Node front;
   private int size;
   
   private class Node {
      private T element;
      
      // recursive structure
      // uses "next" to connect Nodes
      //  - eventually reaches null first value
      private Node next; 
      
   }
   // n - reference of address of value
   // 1 - value stored at address of "n"
   Node n = new Node(1);
   
   // 2 - stored at address of 1st node
   // n - replaces "n" address @ 1 w/ new address at 2
   n = new Node(2, n);
}
