public class FastUnionDS implements DisjointSet {
 
   private int[] id; // component ID's
   private int count; // # of components/groups
   
   // Constructor for class
   public FastUnionDS (int N) {
      count = N;
      id = new int[N];
      for (int i = 0; i < N; i++) {
         id[i] = i; 
      }
   }
   
   // Locate component/group # for p
   // -- goes through chain to get to Parent
   public int find (int p) {
      while (p != id[p]) {
         p = id[p];
      }
      return p;
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
      int pRoot = find(p);
      int qRoot = find(q);
      
      if (pRoot == qRoot) {
         return;
      }
      
      id[pRoot] = qRoot;
      count--;
   }
}
