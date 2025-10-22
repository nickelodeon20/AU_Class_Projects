class StudentTest {
   public static void main(String args[]) {
      // create new Student object
      Student John = new Student();
      
      // create new array and set scores of array
      double[] userArray = new double[5];
      userArray[0] = 90.5;
      userArray[1] = 85.0;
      userArray[2] = 78.5;
      userArray[3] = 92.0;
      userArray[4] = 88.5;
      int i;
      
      // output statements and functions calls
      John.setName("John Smith");
      John.setScores(userArray);
      
      System.out.println("Student Name: " + John.getName());
      
      System.out.print("Student Scores: [");
      for (i = 0; i < 5; i++) { 
         double[] array = John.getScores();
         System.out.print(array[i]);
         if (i < 4) {
            System.out.print(", ");
            }
         }
      System.out.println("]");
      
      System.out.println(John.getName() + "'s Average Test Score: " + John.calculateAvg());

   }
}
