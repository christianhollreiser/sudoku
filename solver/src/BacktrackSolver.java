public class BacktrackSolver implements Solver{

    @Override
    public boolean solve(SudokuPuzzle puzzle) {
        return this.solve(puzzle, 0, 0);
    }

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
                if (Solver.isSafe(sudoku, row, column, value)) {
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


}
