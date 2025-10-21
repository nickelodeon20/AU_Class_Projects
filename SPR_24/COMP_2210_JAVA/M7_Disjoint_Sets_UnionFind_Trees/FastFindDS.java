public class FastFindDS implements DisjointSet {
 
   private int[] id; // component ID's
   private int count; // # of components/groups
   
   // Constructor for class
   public FastFindDS (int N) {
      count = N;
      id = new int[N];
      for (int i = 0; i < N; i++) {
         id[i] = i; 
      }
   }
   
   // Locate component/group # for p
   public int find (int p) {
      return id[p];
   } 
   
   // Check to see if p & q are in the same group
   public boolean connected (int p, int q) {
      return find(p) == find(q);
   }
   
   // Returns # of different components/groups
   public int count() {
      return count;
   }
   
   // Connect two components/groups
   public void union (int p, int q) {
      int pID = find(p);
      int qID = find(q);
      
      if (pID == qID) {
         return;
      }
      
      for (int i = 0; i < id.length; i++) {
         if (id[i] == pID) {
            id[i] = qID;
         }
         count--;
      }
   }
}