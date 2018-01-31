package com.lsu.sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
	* Name       :Mohan, Namratha
	* File       :SudokuSolutionValidator.java
*/

public class SudokuSolutionValidator {
		
	List<Boolean> resultList = new ArrayList<Boolean>();
	List<Thread> validators = new ArrayList<Thread>();
	int[][] inputSolution = new int[9][9];
	
//Reads the input Sudoku-solution file and loads it to a 9*9 2-dimensional array.	
	
	private void readMatrix(String inputFile) {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(inputFile));
			String currentLine = null;
			int i = 0;
			while ((currentLine = bufferedReader.readLine()) != null) 
			{
				String[] currentRow = currentLine.split(",");
				if (currentRow.length != 9) {
					throw new RuntimeException("Row length should be equal to 9");
				}
				for (int j = 0; j < currentRow.length; j++) {
					if((currentRow[j].trim().equals(""))){
						throw new RuntimeException("Row length should be equal to 9");
					}
					else if(Integer.parseInt(currentRow[j].trim())<1 || Integer.parseInt(currentRow[j].trim())>9){
						throw new RuntimeException("Number not in range");
					}
					inputSolution[i][j] = Integer.parseInt(currentRow[j].trim());
				}
				i++;
			}
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
			}
		}

	}

/*Abstract class with a result variable declared in it with 2 functions isResult() and setResult().
result variable is common to both the validators(rowcolumn validator and smallsquare validator). 
So it is separated in a different class to favour inheritence and to avoid code duplication.*/
	
	private abstract class AbstractValidator extends Thread{
		private boolean result;

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

	}

/*Validates each column and row of the input matrix and sets the result variable to false if any 
duplicates are found in a row or column.*/	
	
	private class RowAndColValidator extends AbstractValidator {
		int[] z;

		public RowAndColValidator(int[] z) {
			this.z = z;
		}

		@Override
		public void run() {
			int[] elementCheck = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			for (int i = 0; i < z.length; i++) {
				int index = z[i] - 1;

				if (elementCheck[index] == 0) {
					elementCheck[index] = 1;
				} else {
					setResult(false);
					return;
				}

			}
			setResult(true);
			
		}

	}

/*Validates each subgrid(3*3) of the input matrix and sets the result variable to false if any 
  duplicates are found in a row or column*/	
	
	private class SmallSquareValidator extends AbstractValidator{

		private int r;
		private int c;
		private int[][] a;

		public SmallSquareValidator(int r, int c, int[][] a) {
			this.r = r;
			this.c = c;
			this.a = a;
		}

		@Override
		public void run() {
			int[] subArrCheck = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			for (int i = r; i < r + 3; i++) {
				for (int j = c; j < c + 3; j++) {
					if (subArrCheck[a[i][j] - 1] == 0) {
						subArrCheck[a[i][j] - 1] = 1;
					} else {
						setResult(false);
						return;
					}

				}
			}
			setResult(true);
		}

	}

//Creates row column validator threads and stores it in validators ArrayList.
	
	private void createRowColValidators() {
		int[] rowArray = new int[9];
		int[] colArray = new int[9];
		for (int i = 0; i < inputSolution.length; i++) {
			for (int j = 0; j < inputSolution.length; j++) {
				rowArray[j] = inputSolution[i][j];
				colArray[j] = inputSolution[j][i];

			}
			RowAndColValidator rowValidator = new RowAndColValidator(rowArray);
			RowAndColValidator colValidator = new RowAndColValidator(colArray);
			validators.add(rowValidator);
			validators.add(colValidator);

		}
	}

//Creates small square validator threads and stores it in validators ArrayList.	
	
	private void createSmallSquareValidators() {
		for (int rowStart = 0; rowStart < inputSolution.length; rowStart += 3) {
			for (int colStart = 0; colStart < inputSolution.length; colStart += 3) {
				SmallSquareValidator squareValidator = new SmallSquareValidator(rowStart, colStart, inputSolution);
				validators.add(squareValidator);
			}
		}

	}

//Starts all threads in validators ArrayList
	
	private void startAllValidators() {
		for (Thread validator : validators) {
			validator.start();
		}
	}

/*Waits until all the validator threads are complete. Loads all result values from individual 
  thread to the resultList ArrayList */
	
	private void waitUntilAllValidatorsToComplete() {
		for (Thread validator : validators) {
			try {
				validator.join();
				resultList.add(((AbstractValidator) validator).isResult());  
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

/*Checks each of the values stored by each thread in resultList ArrayList. If any value in the 
  List is found to be false, it prints as an Invalid Solution else prints as a Valid Solution.*/
	
	private void printResult() {
		boolean isInValid = false;
		for (Boolean result : resultList) {
			if (!result) {
				isInValid = true;
				break;
			}
		}
		if (isInValid) {
			System.out.println("The Sudoku Puzzle Solution is Invalid");
		} else {
			System.out.println("The Sudoku Puzzle Solution is Valid");
		}
	}


	public static void main(String[] args) {
		SudokuSolutionValidator solutionValidator = new SudokuSolutionValidator();
		solutionValidator.readMatrix(args[0]);
		solutionValidator.createRowColValidators();
		solutionValidator.createSmallSquareValidators();
		solutionValidator.startAllValidators();
		solutionValidator.waitUntilAllValidatorsToComplete();
		solutionValidator.printResult();
	}

}