package generator;

import gui.SudokuFrame;
import solver.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SudokuPuzzle implements Cloneable {

    private static final int DEFAULT_SIZE = 9;
    private static final Level DEFAULT_LEVEL = Level.EASY;

    private final Level level;
    private Grid grid;
    private SudokuFrame frame;

    /** Contains the possible levels of difficulty for the sudoku puzzle. */
    private enum Level {
        EASY{
            @Override
            public String toString() {
                return "easy";
            }
        },

        MEDIUM{
            @Override
            public String toString() {
                return "medium";
            }
        },

        HARD{
            @Override
            public String toString() {
                return "hard";
            }
        };
    }


    /** Initialises sudoku puzzle with default size (9x9) and default difficulty level (easy). */
    public SudokuPuzzle() throws CloneNotSupportedException {
        this(DEFAULT_LEVEL.ordinal(), DEFAULT_SIZE); // TODO: instead of using ordinal use EnumMap?
    }

    /**
     * Initialises sudoku puzzle from two-dimensional array.
     *
     * @param grid: two-dimensional array containing initial puzzle values
     */
    public SudokuPuzzle(int[][] grid) throws CloneNotSupportedException {
        this.grid = new Grid(grid);
        this.level = DEFAULT_LEVEL; // TODO: update this with whatever rule used to determine difficulty
        this.frame = new SudokuFrame(this);
    }

    /**
     * Initialises sudoku puzzle with given size and difficulty level.
     *
     * @param level: difficulty level
     * @param size: size of sudoku puzzle
     */
    public SudokuPuzzle(int level, int size) throws CloneNotSupportedException {
        this.level = Level.values()[level];
        this.grid = new Grid(size);
        this.frame = new SudokuFrame(this);

        // fill grid
        this.fillGrid();

        // remove values according to difficulty
        this.removeValues();
    }

    /** Fills the sudoku grid (completely) with a valid permutation of numbers in [1, size]. */
    private void fillGrid(){
        // solve empty puzzle to fill
        solver.SudokuSolver.solve(this, 0, 0);
    }

    private void clearGrid(){
        for(int row = 0; row < this.getSize(); row++){
            for(int col = 0; col < this.getSize(); col++){
                this.setEntry(row, col, 0);
            }
        }
    }

    /** Removes values in the puzzle to obtain a well-defined sudoku puzzle, according to given difficulty level. */
    private void removeValues() throws CloneNotSupportedException {
        Set<Position> possiblePositions = new LinkedHashSet<>();
        for(int row = 0; row < this.getSize(); row++){
            for(int col = 0; col < this.getSize(); col++){
                possiblePositions.add(new Position(row, col));
            }
        }
        int numAttempts = 0;
        int targetNumRemoved = 30;
        int numRemoved = 0;
        while(numRemoved < targetNumRemoved){
            int index = (int)(Math.random()*possiblePositions.size());
            Position p = (Position) possiblePositions.toArray()[index];
            int temp = this.getEntry(p.row, p.col);
            this.setEntry(p.row, p.col, 0);
            SudokuPuzzle initial = (SudokuPuzzle) this.clone();
            boolean validPuzzle = solver.SudokuSolver.solve(initial, 0, 0);
            if(validPuzzle){
                boolean notValidPuzzle = solver.SudokuSolver.solve((SudokuPuzzle) this.clone(), 0, 0, p.row, p.col, initial.getEntry(p.row, p.col));
                if(!notValidPuzzle){
                    numRemoved++;
                } else {
                    this.setEntry(p.row, p.col, temp);
                }
            } else {
                this.setEntry(p.row, p.col, temp);
            }
            possiblePositions.remove(p);
            numAttempts++;
        }
        System.out.println("Number of attempts: " + numAttempts);
    }

    /** Returns the difficulty level of the Sudoku Puzzle. */
    public Level getLevel(){
        return this.level;
    }

    /** Returns the size of the Sudoku Puzzle. */
    public int getSize(){
        return this.grid.getSize();
    }

    public int getEntry(int row, int column){
        return this.grid.getEntry(row, column);
    }

    public void setEntry(int row, int column, int value){
        this.grid.setEntry(row, column, value);
        if(this.frame != null) {
            this.frame.setButtonValue(row, column, value);
        }
    }

    public boolean setSafeEntry(int row, int column, int value){
        if(value == 0 || SudokuSolver.isSafe(this, row, column, value)){
            this.grid.setEntry(row, column, value);
            if(this.frame != null) {
                this.frame.setButtonValue(row, column, value);
            }
            return true;
        }
        return false;
    }

    public void print(){
        this.grid.print();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SudokuPuzzle clone = (SudokuPuzzle) super.clone();
        clone.grid = (Grid) this.grid.clone();
        clone.frame = null;
        return clone;
    }

    public boolean solve(){
        boolean solved = SudokuSolver.solve(this, 0, 0);
        return solved;
    }

    public void newPuzzle() throws CloneNotSupportedException {
        this.clearGrid();
        this.fillGrid();
        this.removeValues();
    }


    public static void main(String[] args) throws CloneNotSupportedException {
        SudokuPuzzle puzzle = new SudokuPuzzle();
    }
}
