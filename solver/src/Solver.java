public interface Solver {

    public boolean solve(SudokuPuzzle puzzle);


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

}
