class studentTest {
   public static void main (String args[]) {
      // create 3 new Student objects
      Student Nick = new Student("Nick", "S", 123, "123@gmail.com", "CS");
      Student Duc = new Student("Duc", "Vu", 456, "456@juno.com", "Business");
      Student Andrew = new Student("Andrew", "Castel", 789, "789@hotmail.com", "E.E.");
      
      // print 3 student objects' info
      System.out.println(Nick);
      System.out.println(Duc.toString());
      System.out.println(Andrew.toString());
      
      // change email 
      Nick.setEmail("newEMAIL@mail.com");
      
      System.out.println(Nick.toString());
      }
    
  }
    
