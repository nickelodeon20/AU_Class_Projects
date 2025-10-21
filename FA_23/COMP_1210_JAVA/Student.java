class Student {
   // initialize Student's name and scores 
   private String name;
   private double[] scores = new double[5];
   
   // set Student name
   public void setName(String newName) {
      name = newName;
    }
   // set Student scores
   public void setScores(double[] newScores) {
      scores = newScores;
    }
   // retrieve Student name
   public String getName() {
      return name;
    }
   // retrieve Student scores
   public double[] getScores() {
      return scores;
    }
   // calculate average of scores of Student
   public double calculateAvg() {
      double total = 0;
      int i = 0;
      double avg = 0;
      
      for (i = 0; i < 5; i++) {
         total += scores[i];
         }
      
      avg = total / 5.0;
      return avg;
    }
     
}