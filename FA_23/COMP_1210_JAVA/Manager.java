class Manager extends Employee {
   private String department;
   
   // set Manager properties, using employee methods
   public Manager (String name, int EmployeeID, String userDpmt) {
      super(name, EmployeeID);
      department = userDpmt;
    }
   public String getDepartment() {
      return department;
    }
}
