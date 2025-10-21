class Employee {
   private String name;
   private int employeeID; 
   
   // set private variables
   public Employee (String userName, int num) {
      name = userName;
      employeeID = num;
    }
   // return details all in one string
   public String getDetails() {
      String details = "";
      details += "Employee ID: " + employeeID + "\n";
      details += "Name: " + name;
      return details;
    }
}
