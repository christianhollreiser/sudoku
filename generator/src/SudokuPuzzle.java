
public class SudokuPuzzle {

    private static final int DEFAULT_SIZE = 9;
    private static final Level DEFAULT_LEVEL = Level.EASY;

    private final int size;
    private final Level level;
    private Grid grid;

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
    public SudokuPuzzle(){
        this(DEFAULT_LEVEL.ordinal(), DEFAULT_SIZE); // TODO: instead of using ordinal use EnumMap?
    }

    /**
     * Initialises sudoku puzzle from two-dimensional array.
     *
     * @param grid: two-dimensional array containing initial puzzle values
     */
    public SudokuPuzzle(int[][] grid){
        this.grid = new Grid(grid);
        this.size = this.grid.getSize();
        this.level = DEFAULT_LEVEL; // TODO: update this with whatever rule used to determine difficulty
    }

    /**
     * Initialises sudoku puzzle with given size and difficulty level.
     *
     * @param level: difficulty level
     * @param size: size of sudoku puzzle
     */
    public SudokuPuzzle(int level, int size){
        this.size = size;
        this.level = Level.values()[level];
        this.grid = new Grid(this.size);

        // fill grid
        this.fillGrid();

        // remove values according to difficulty
        this.removeValues();
    }

    /** Fills the sudoku grid (completely) with a valid permutation of numbers in [1, size]. */
    private void fillGrid(){
        // algorithm to fill sudoku


    }

    /** Removes values in the puzzle to obtain a well-defined sudoku puzzle, according to given difficulty level. */
    private void removeValues(){

    }

    /** Returns the difficulty level of the Sudoku Puzzle. */
    public Level getLevel(){
        return this.level;
    }

    /** Returns the size of the Sudoku Puzzle. */
    public int getSize(){
        return this.size;
    }

    public int getEntry(int row, int column){
        return this.grid.getEntry(row, column);
    }

    public void setEntry(int row, int column, int value){
        this.grid.setEntry(row, column, value);
    }

    public void print(){
        this.grid.print();
    }
}
