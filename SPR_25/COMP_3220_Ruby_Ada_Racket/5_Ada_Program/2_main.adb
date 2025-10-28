with Ada.Text_IO, Ada.Float_Text_IO, Ada.Integer_Text_IO, Assgn;
use Ada.Text_IO, Ada.Float_Text_IO, Ada.Integer_Text_IO, Assgn;

procedure Main is

   My_Array, Param1, Param2, Param3, Param4a, Param4b, Param5a, Param5b, Param6 : BINARY_ARRAY;
   Result2, Result3, Result4 : BINARY_ARRAY;
   Result1 : INTEGER;
   Test : HALF_INT := 55;
   Test2 : INTEGER := 54003;
   
begin --start main
   
   -- TEST CASE 1
   Init_Array(My_Array); --Test Init_Array

   Put_Line("Testing Reverse_Bin_Arr.");
   Put_Line("Printing array before reversal ( 59260 ): ");
   Param1 := Int_To_Bin (59260);
   Print_Bin_Arr(Param1);

   Reverse_Bin_Arr(Param1);

   Put_Line("Reversed array is: ( 16103 ): ");
   Print_Bin_Arr(Param1);

   
   -- TEST CASE 2
   Put_Line("Testing Int_To_Bin with the number: " & Integer'Image(Test));
   Param2 := Int_To_Bin(Test);

   Put_Line("Binary array was: ");
   Print_Bin_Arr(Param2);

   
   -- TEST CASE 3
   Put_Line("Testing Bin_To_Int with the following array: ( 55 ): ");
   Param3 := (0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1);
   Print_Bin_Arr(Param3);

   Result1 := Bin_To_Int(Param3);
   Put_Line("Function returned: " & Integer'Image(Result1));

   
   -- TEST CASE 4
   Put_Line("Testing overloaded + operator for INTEGER and BINARY_ARRAY");
   Put_Line("Testing with INTEGER: " & Integer'Image(Test) & " and BINARY_ARRAY ( 59260 ): ");
   Param4a := Int_To_Bin(55);
   Param4b := (1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0);
   Print_Bin_Arr(Param4b);

   Result2 := Test + Param4b;

   Put_Line("Resulting array is: ");
   Print_Bin_Arr(Result2);

   -- TEST CASE 5
   Put_Line("Testing overloaded + operator for BINARY_ARRAY and BINARY_ARRAY");
   Put_Line("Testing with BINARY_ARRAY ( 59260 ): ");
   Param5a := Param3;
   Print_Bin_Arr(Param5a);
   Put_Line("and BINARY_ARRAY ( 55 ): ");
   Param5b := Param4b;
   Print_Bin_Arr(Param5b);

   Result3 := Param5a + Param5b;

   Put_Line("Resulting array is: ");
   Print_Bin_Arr(Result3);

   -- TEST CASE 6
   Put_Line("Testing overloaded - operator for INTEGER and BINARY_ARRAY");
   Put_Line("Testing with INTEGER: " & Integer'Image(Test2) & " and BINARY_ARRAY ( 62907 ): ");
   Param6 := (1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1);
   Print_Bin_Arr(Param6);
   
   Result4 := Test2 - Param6;

   Put_Line("Resulting array is: ");
   Print_Bin_Arr(Result4);
end Main;
