package solver;

import generator.*;

public class SudokuSolver {

    public static boolean solve(SudokuPuzzle sudoku, int row, int column){
        boolean lastRow = row == sudoku.getSize()-1;
        boolean lastColumn = column == sudoku.getSize()-1;
        int numValidSolutions = 0;
        boolean valid;
        if(sudoku.getEntry(row, column) != 0){
            if(!lastColumn){
                valid = solve(sudoku, row, column+1);
            } else if(!lastRow){
                valid = solve(sudoku, row + 1, 0);
            } else {
                valid = true;
            }
            return valid;
        } else {
            for (int value = 1; value <= sudoku.getSize(); value++) {
                if (isSafe(sudoku, row, column, value)) {
                    sudoku.setEntry(row, column, value);
                    if (!lastColumn) {
                        valid = solve(sudoku, row, column + 1);
                    } else if (!lastRow) {
                        valid = solve(sudoku, row + 1, 0);
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


    public static void main(String[] args){

        int[][] test = {{0,0,3,0,2,0,6,0,0},
                {9,0,0,3,0,5,0,0,1},
                {0,0,1,8,0,6,4,0,0},
                {0,0,8,1,0,2,9,0,0},
                {7,0,0,0,0,0,0,0,8},
                {0,0,6,7,0,8,2,0,0},
                {0,0,2,6,0,9,5,0,0},
                {8,0,0,2,0,3,0,0,9},
                {0,0,5,0,1,0,3,0,0}};
        SudokuPuzzle puzzle = new SudokuPuzzle(test);
        SudokuSolver.solve(puzzle, 0, 0);
        assert isValidSolution(puzzle) == true;
    }
}

