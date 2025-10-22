import java.util.Scanner;

public class LabProgram {
  public static String checkCharacter(String userString, int index) {
   char userChar = userString.charAt(index);
   
   if (Character.isLetter(userChar) == true) {
      return "Character '" + userChar +"' is a letter";
   } else if (Character.isDigit(userChar) == true) {
      return "Character '" + userChar +"' is a digit";
   } else if (Character.isWhitespace(userChar) == true) {
      return "Character '" + userChar +"' is a white space";
   } else {
      return "Character '" + userChar +"' is a unknown";
   }
   }
  public static void main ( String[] args) {
   
   
   System.out.println(checkCharacter("happy birthday", 2));
   System.out.println(checkCharacter("happy birthday", 5));
   System.out.println(checkCharacter("happy birthday 2 you", 15));
   System.out.println(checkCharacter("happy birthday!", 14));
   
  }
}
