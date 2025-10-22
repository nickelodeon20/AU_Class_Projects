class Student {
   // private variables for all attributes of Student objects
   private String firstName; 
   private String lastName; 
   private int studentId;
   private String email;
   private String major;
   
   // setter method using input for Student objects
   public Student (String fName, String lName, int id, String Email, String Major) {
      firstName = fName;
      lastName = lName;
      studentId = id;
      email = Email;
      major = Major;
   }
   // retrieve private var. firstName
   public String getFirstName() {
      return firstName;
   }
   // retrieve private var. lastName
   public String getLastName() {
      return lastName;
   }
   // retrieve private var. studentId
   public int getStudentId() {
      return studentId;
   }
   // retrieve private var. email
   public String getEmail() {
      return email;
   }
   // retrieve private var. major
   public String getMajor() {
      return major;
   }
   // change email to user-input var newEmail
   public void setEmail(String newEmail) {
      email = newEmail;
   }
   public String toString() {
      return "Full Name: " + firstName + " " + lastName + "\n" +
      "Student ID: " + studentId + "\n" +
      "Email: " + email + "\n" +
      "Major: " + major + "\n" +
      "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
   }
}
