class Student {
   // set Student
   private String name;
   private double[] scores = new double[5];
   
   public void setName(String newName) {
      name = newName;
    }
   public void setScores(double[] newScores) {
      scores = newScores;
    }
   public String getName() {
      return name;
    }
   public double[] getScores() {
      return scores;
    }
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