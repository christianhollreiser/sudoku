package solver;

import generator.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuSolver {
    public static final int NULL_ROW = -1;
    public static final int NULL_COLUMN = -1;
    public static final int NULL_VALUE = -1;

    public static boolean solve(SudokuPuzzle sudoku, int row, int column){
        return SudokuSolver.solve(sudoku, row, column, NULL_ROW, NULL_COLUMN, NULL_VALUE);
    }

    public static boolean solve(SudokuPuzzle sudoku, int row, int column, int rowTarget, int colTarget, int notAllowed){
        Integer[] VALUES = new Integer[sudoku.getSize()];
        for(int i = 0; i < sudoku.getSize(); i++){
            VALUES[i] = i+1;
        }
        boolean lastRow = row == sudoku.getSize()-1;
        boolean lastColumn = column == sudoku.getSize()-1;
        boolean valid;
        if(sudoku.getEntry(row, column) != 0){
            if(!lastColumn){
                valid = solve(sudoku, row, column+1, rowTarget, colTarget, notAllowed);
            } else if(!lastRow){
                valid = solve(sudoku, row + 1, 0, rowTarget, colTarget, notAllowed);
            } else {
                valid = true;
            }
            return valid;
        } else {
            Collections.shuffle(Arrays.asList(VALUES));
            for (int i = 0; i < sudoku.getSize(); i++) {
                int value = VALUES[i];
                if ((value != notAllowed || rowTarget != row || colTarget != column) && isSafe(sudoku, row, column, value)) {
                    sudoku.setEntry(row, column, value);
                    if (!lastColumn) {
                        valid = solve(sudoku, row, column + 1, rowTarget, colTarget, notAllowed);
                    } else if (!lastRow) {
                        valid = solve(sudoku, row + 1, 0, rowTarget, colTarget, notAllowed);
                    } else {
                        valid = true;
                    }
                    if (valid) {
                        return true;
                    } else {
                        // replace with 0 again;
                        sudoku.setEntry(row, column, 0);
                    }

                }
            }
        }

        return false;
    }

    public static boolean isSafe(SudokuPuzzle sudoku, int row, int column, int value){
        // check unique in row
        for(int r = 0; r < sudoku.getSize(); r++){
            if(sudoku.getEntry(r, column) == value && r != row){
                return false;
            }
        }

        // check unique in column
        for(int c = 0; c < sudoku.getSize(); c++){
            if(sudoku.getEntry(row, c) == value && c != column){
                return false;
            }
        }

        // check unique in subgrid
        int subgridSize = (int) Math.sqrt(sudoku.getSize());
        int startRow = row - row % subgridSize;
        int startCol = column - column % subgridSize;
        for(int r = startRow; r < startRow + subgridSize; r++){
            for(int c = startCol; c < startCol + subgridSize; c++){
                if(sudoku.getEntry(r,c) == value && r != row && c != column){
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isValidSolution(SudokuPuzzle finishedPuzzle){
        for(int r = 0; r < finishedPuzzle.getSize(); r++){
            for(int c = 0; c < finishedPuzzle.getSize(); c++){
                if(!isSafe(finishedPuzzle, r, c, finishedPuzzle.getEntry(r,c))){
                    return false;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) throws CloneNotSupportedException {

        int[][] test = {{0,0,3,0,2,0,6,0,0},
                        {9,0,0,3,0,5,0,0,1},
                        {0,0,1,8,0,6,4,0,0},
                        {0,0,8,1,0,2,9,0,0},
                        {7,0,0,0,0,0,0,0,8},
                        {0,0,6,7,0,8,2,0,0},
                        {0,0,2,6,0,9,5,0,0},
                        {8,0,0,2,0,3,0,0,9},
                        {0,0,5,0,1,0,3,0,0}};
        int[][] testEmpty = new int[9][9];
        SudokuPuzzle puzzle = new SudokuPuzzle(test);
        SudokuPuzzle copy = (SudokuPuzzle) puzzle.clone();
        SudokuSolver.solve(copy , 0, 0);
        boolean check = SudokuSolver.solve((SudokuPuzzle) puzzle.clone(), 0, 0, 4, 4, copy.getEntry(4, 4));
        assert check == false;
    }
}

