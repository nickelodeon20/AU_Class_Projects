with Ada.Numerics.Float_Random;
use Ada.Numerics.Float_Random;
with Ada.Text_IO;
with Ada.Integer_Text_IO;
with Ada.Float_Text_IO;
use Ada.Text_IO;
use Ada.Integer_Text_IO;
use Ada.Float_Text_IO;


package body Assgn is 

   procedure Init_Array (Arr: in out BINARY_ARRAY) is
   Gen : Generator;
   begin
    Reset(Gen); -- Initialize the random number generator
   for I in Arr'Range loop
        if Random(Gen) < 0.5 then
            Arr(I) := 0;
         else
            Arr(I) := 1;
         end if;
    end loop;
   end Init_Array;


    procedure Reverse_Bin_Arr (Arr : in out BINARY_ARRAY) is
    Temp : BINARY_NUMBER;
   begin
    for I in Arr'First .. Arr'First + (Arr'Length / 2) - 1 loop
        Temp := Arr(I);
        Arr(I) := Arr(Arr'Last - (I - Arr'First));
        Arr(Arr'Last - (I - Arr'First)) := Temp;
    end loop;
   end Reverse_Bin_Arr;


    procedure Print_Bin_Arr (Arr : in BINARY_ARRAY) is
    begin
        for I in Arr'Range loop
            Put(Integer'Image(Arr(I)));
        end loop;
        New_Line;
    end Print_Bin_Arr;


    function Int_To_Bin(Num : in INTEGER) return BINARY_ARRAY is
    Result : BINARY_ARRAY := (others => 0);
    Temp : INTEGER := Num;
   begin
    if Temp < 0 or Temp > 65535 then
        raise Constraint_Error with "Integer out of range for BINARY_ARRAY";
    end if;
    for I in reverse Result'Range loop
        Result(I) := Temp mod 2;
        Temp := Temp / 2;
    end loop;
    return Result;
   end Int_To_Bin;

   
   function Bin_To_Int (Arr : in BINARY_ARRAY) return INTEGER is
        Result : INTEGER := 0;
        Weight : INTEGER := 1;
   begin
        for I in reverse Arr'Range loop
            Result := Result + (Arr(I) * Weight);
            Weight := Weight * 2;
        end loop;
        return Result;
   end Bin_To_Int;

   
   function "+" (Left, Right : in BINARY_ARRAY) return BINARY_ARRAY is
        Result : BINARY_ARRAY := (others => 0);
        Carry : INTEGER := 0;
   begin
        for I in reverse Left'Range loop
            Result(I) := (Left(I) + Right(I) + Carry) mod 2;
            Carry := (Left(I) + Right(I) + Carry) /2;
        end loop;
        if Carry > 0 then
            raise Constraint_Error with "Overflow in addition of BINARY_ARRAYs";
        end if;
        return Result;
   end "+";

   
   function "+" (Left : in INTEGER; Right: in BINARY_ARRAY) return BINARY_ARRAY is
        Temp : BINARY_ARRAY := Int_To_Bin(Left);
   begin
        return Temp + Right;
   end "+";

   
   function "-" (Left, Right : in BINARY_ARRAY) return BINARY_ARRAY is
      TwosComp : BINARY_ARRAY := (others => 0);
      Result : BINARY_ARRAY := (others => 0);
      Carry : INTEGER := 1; -- Start with 1 to add during 2's complement
   begin
      if Left < Right then
         Put_Line("Negative result, computing 2's complement subtraction.");
         for I in Right'Range loop
            TwosComp(I) := 1 - Right(I); -- 1's complement
         end loop;

         for I in reverse TwosComp'Range loop
            declare
               Sum : Natural := TwosComp(I) + Carry;
            begin
               TwosComp(I) := Sum mod 2; -- 2's complement
               Carry := Sum / 2;
            end;
         end loop;

         Carry := 0;
         for I in reverse Left'Range loop
            declare
               Sum : Natural := Left(I) + TwosComp(I) + Carry;
            begin
               Result(I) := Sum mod 2; -- Binary addition
               Carry := Sum / 2;
            end;
         end loop;

         if Carry > 0 then
            Put_Line("Overflow in subtraction of BINARY_ARRAYs.");
         end if;
         return Result;
      else
        -- Case 2: Regular subtraction (Left >= Right)
        Put_Line("Regular subtraction detected.");

        -- Perform binary subtraction with borrow
        Carry := 0; -- Borrow
        for I in reverse Left'Range loop
            Result(I) := (Left(I) - Right(I) - Carry + 2) mod 2; -- Binary subtraction
            if Left(I) < Right(I) + Carry then
                Carry := 1; -- Borrow from the next higher bit
            else
                Carry := 0; -- No borrow needed
            end if;
        end loop;

        return Result;
      end if;
   end "-";

   
   function "-" (Left : in INTEGER; Right : in BINARY_ARRAY) return BINARY_ARRAY is
        Temp : BINARY_ARRAY := Int_To_Bin(Left);
   begin
        return Temp - Right;
   end "-";
end Assgn;
