import java.util.Scanner;

class LabProgram {
  public static int fibonacci(int input) {
    int num1 = 0;
    int num2 = 1;
    int num3 = 1;
    int i = 0;
    int fibonacci = 0;
    
    if (input > 0) {
      while (i < input-1) {
        num3 = num1 + num2;
        num1 = num2;
        num2 = num3;
        i++;
      }
      fibonacci = num3;
      return fibonacci;
     } else if (input == 0) {
      return fibonacci;
     } else {
      return -1;
     }
  
  }
  public static void main ( String[] args) {
    Scanner scnr = new Scanner(System.in);
    int input = scnr.nextInt();
  
    System.out.println("fibonacci(" + input + ") is " + fibonacci(input));
  }
}
