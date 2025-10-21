class Main {
   public static void main (String args[]) {
      // create Manager "John" and Developer "Tim" with print statements
      Manager John = new Manager("John", 12345, "Math");
      
      System.out.println("Manager Details: ");
      System.out.println(John.getDetails());
      System.out.println("Department: " + John.getDepartment());
      
      System.out.println("------------------------------------");
      
      Developer Tim = new Developer("Tim", 67890, "Java");
      
      System.out.println("Developer Details: ");
      System.out.println(Tim.getDetails());
      System.out.println("Programming Language: " + Tim.getPrgmLang());
    }
}