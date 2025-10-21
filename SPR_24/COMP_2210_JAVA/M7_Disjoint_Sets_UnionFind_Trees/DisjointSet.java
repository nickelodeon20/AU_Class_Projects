public interface DisjointSet {

   ////////
   // Combine components containg q & p
   ////////
   void union (int p, int q);
   
   ////////
   // Return component ID for p
   ////////
   int find (int p);
   
   ////////
   // Check if q & p are in same component
   ////////
   boolean connected (int p, int q);
   
   ////////
   // Returns # of connected components (different groups)
   ////////
   int count();
}