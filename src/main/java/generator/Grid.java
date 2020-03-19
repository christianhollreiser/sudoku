package generator;

import java.util.*;

public class Grid {
    private Square[][] grid;
    private final int size;

    public Grid(int size){
        this.size = size;
        this.grid = new Square[size][size];
    }

    public Grid(int[][] grid){
        this.size = grid.length;
        this.grid = new Square[grid.length][grid[0].length];
        for(int row = 0; row < this.grid.length; row++){
            for(int col = 0; col < this.grid[0].length; col++)
                this.grid[row][col] = new Square(grid[row][col]);
        }
    }

    public Square getSquare(int row, int column){
        assert row < grid.length && row >= 0 && column < grid[0].length && column >= 0;
        return this.grid[row][column];
    }

    public int getEntry(int row, int column){
        return this.getSquare(row, column).getEntry();
    }

    public void setEntry(int row, int column, int value){
        this.getSquare(row, column).changeEntry(value);
    }

    public Set<Integer> getCandidates(int row, int column){
        return this.getSquare(row, column).getCandidates();
    }

    public int getSize(){
        return this.size;
    }

    public void print(){
        for(Square[] row : this.grid){
            for(Square square : row){
                System.out.print(square.getEntry());
            }
            System.out.println();
        }
    }
}