Name       :Mohan, Namratha
File       :README.txt

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Introduction
----------------
Sudoku Solution Validator: 
A Sudoku puzzle is a 9X9 grid which contains 9 columns, 9 rows as well as nine 3X3 subgrids and it must contain all of the digits from 1 through 9. 
There should be no duplicates in any row or any column or any of the sub-grids.
The Java multithreaded program which I have written will validate if the given Sudoku Solution included in an Input text file is Valid or Invalid.
The code also validates the test cases like:
* The number in the given Sudoku Solution is well within the range of 1 through 9
* If any digit is missed in any of the row/column in the given Sudoku Solution
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Code Execution Steps:
---------------------
Test case 1: To check the given Sudoku Solution with a valid input

Compile and execute InputMatrix_Valid.txt

- InputMatrix_Valid.txt is the input file which contains a valid 9x9 sudoku puzzle solution and this is passed as the argument while executing.

Expected Output: The Sudoku Puzzle Solution is Valid
Reason: The code verifies each row, each column and each of the 3X3 subgrids if it contains the digits 1 through 9 and displays 
the result as Valid Solution.
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Test case 2: To check the given Sudoku Solution with an invalid input

Compile and execute InputMatrix_Invalid.txt

- InputMatrix_Invalid.txt is the input file which contains an Invalid 9x9 sudoku puzzle solution and this is passed as the argument while executing.

Expected Output: The Sudoku Puzzle Solution is Invalid
Reason: The code verifies each row, each column and each of the 3X3 subgrids if it contains the digits 1 through 9. The number "3" is 
repeated in row 4, column 6. So it displays as Invalid Solution.
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Test case 3: To check the given Sudoku Solution contains an out of range number

Compile and execute InputMatrix_OutofRange.txt

- InputMatrix_OutofRange.txt is the input file which contains a 9x9 sudoku puzzle solution with an out of range number and this is passed as the argument
while executing.

Expected Output: Throws a runtime exception with an error message "Number not in range"
Reason: The code verifies each row, each column and each of the 3X3 subgrids if it contains the digits 1 through 9. The number "10" is mentioned
in row 7, column 9. Since 10 does not fall in the range of 1 through 9, it throws an exception.
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Test case 4: To check the given Sudoku Solution for any digit missing in the 9X9 grid

Compile and execute InputMatrix_DigitMissing.txt

- InputMatrix_DigitMissing.txt is the input file which contains a 9x9 sudoku puzzle solution with a missing digit and this is passed as the argument
while executing.

Expected Output: Throws a runtime exception with an error message "Row length should be equal to 9"
Reason: The code verifies each row, each column and each of the 3X3 subgrids if it contains the digits 1 through 9. A digit is missing in 
row 5, column 5. So, it throws an exception.
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


