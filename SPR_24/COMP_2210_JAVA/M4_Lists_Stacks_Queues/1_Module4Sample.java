public class ArrayIndexedList<T> implements IndexedList<T> {
   private T[] elements;
   private int rear;

   public boolean add(T element) {
      if (isFull()) {
         resize(elements.length * 2);
      }
      // last element in (not null) is now element after resize
      elements[rear] = element;
      rear++;
      return true;
   }
   
   public boolean add(T element, int index) {
      if (index == rear) {
         // calls previous add method if call lines up
         // list.add("A", 0) & List.add("B", 1) --> uses other add
         return add(element)
      }
      // must be index in range; 0 < index < rear
      if (!validIndex(index)) {
         return false;
      }
      // resize if array is full
      if (isFull()) {
         resize(elements.length * 2);
      }
      // move everything over 1 to leave room to insert new value
      // at index listed 
      shiftRight(index);
      elements[rear] = element;
      rear++;
      return true;
   }
}

public class LinkedIndexedList<T> implements IndexedList<T> {
   private Node front;
   private Node rear;
   private int size;
   
   public boolean add(T element, int index) {
      if (index == size) {
         // uses first basic add method
         return add(element);
      }   
      // 0 < index < size 
      if (!validIndex(index)) {
         return false;
      }
      // dont even need new node until now
      Node n = new Node(element);
      
      
      if (index == 0) {
         // make new node aware of list
         n.next = front; 
         // make list aware of new node  
         front.prev = n;
         // connect two together
         front = n
      } else {
         Node p = front;
         // go through array, search for desired index w/ for loop
         for (int i = 0; i < index; i ++) {
            p = p.next;
         }
         // once found, connecting nodes together
         n.next = p;
         n.prev = p.prev;
         p.prev.next = n;
         p.prev = n;
      }
   }
   S = {A, B, C}
   
   P(S) = { { *empty*}, {A}, {B}, {C}, {AB}, {AC}, {BC}, {ABC} }  
   
   // Cannot initially build power set b/c exponential cost
   // Call above runs through set – DOES NOT CREATE
   Iterator<Set T>(S) psi = s.powerSetIterator()
   
   while (psi.hasNext()) {
      system.out.print(psi.next());
      // constructs and returns one ("the next one") element
   }
}

