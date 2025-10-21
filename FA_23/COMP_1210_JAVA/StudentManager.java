// imports
import java.util.ArrayList;
import java.util.Scanner;

class StudentManager {
   // create private ArrayList
   private ArrayList<String> studentList = new ArrayList<String>();
   
   // return ArrayList
   public ArrayList getStudents() {
      return studentList;
      }
   // add student to ArrayList
   public void addStudent(String student) {
      studentList.add(student);
      }
   // run program function
   public void run(StudentManager manager) {   
      Scanner scnr = new Scanner(System.in);
      int choice = scnr.nextInt();
      
      // continuous loop until user-input breaks it
      while (true) {
         // adding a studnet
         if (choice == 1) {
            System.out.println("Enter the student's name: ");
            String name = scnr.next();
            
            manager.addStudent(name);
            System.out.println(name + " has been added to the student list.");
            System.out.println("-------------------------------------------");
         // printing students list
         } else if (choice == 2) {
            int i;
            ArrayList<String> list = manager.getStudents();
            
            System.out.println("Student List: ");
            for (i = 0; i < list.size(); i++) {
              System.out.println(list.get(i));
              }
            System.out.println("-------------------------------------------");
         // break loop
         } else if (choice == 3) {
            System.out.println("Exiting Student Manager. Goodbye!");
            break;
         // if any wrong input is sent in
         } else {
            System.out.println("Invalid.");
            System.out.println("-------------------------------------------");
         }
         
         System.out.println("Student Manager Menu: ");
         System.out.println("1. Add Student");
         System.out.println("2. Display Student List");
         System.out.println("3. Exit");
         System.out.println("Enter your choice: ");

         choice = scnr.nextInt();
       }

   }
   public static void main(String args[]) {
      // create new StudentManager object
      StudentManager manager = new StudentManager();
      
      System.out.println("Student Manager Menu: ");
      System.out.println("1. Add Student");
      System.out.println("2. Display Student List");
      System.out.println("3. Exit");
      System.out.println("Enter your choice: ");
      
      manager.run(manager);
      }


}