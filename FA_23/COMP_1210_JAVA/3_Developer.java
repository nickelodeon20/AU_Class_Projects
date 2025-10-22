class Developer extends Employee {
   private String programmingLanguage;
   
   // set Developer properties, using employee methods
   public Developer (String name, int EmployeeID, String language) {
      super(name, EmployeeID);
      programmingLanguage = language;
    }
   public String getPrgmLang() {
      return programmingLanguage;
    }
}
