package generator;

import java.util.*;

public class Grid implements Cloneable{
    private Square[][] grid;
    private final int size;

    public Grid(int size){
        this.size = size;
        this.grid = new Square[size][size];
        for(int row = 0; row < this.grid.length; row++){
            for(int col = 0; col < this.grid[0].length; col++)
                this.grid[row][col] = new Square();
        }
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
        for(int i = 0; i < this.getSize(); i++){
            Square[] row = this.grid[i];
            if(i % 3 == 0){
                System.out.print(" ");
                for(Square square : row){
                    System.out.print("****");
                }
            } else {
                System.out.print(" ");
                for(Square square : row){
                    System.out.print("----");
                }
            }
            System.out.println();
            for(int j = 0; j < this.getSize(); j++){
                if(j != 0 && j%3==0){
                    System.out.print(" || " + row[j].getEntry());
                } else {
                    System.out.print(" | " + row[j].getEntry());
                }
            }
            System.out.print(" | " );
            System.out.println();

            if(i == this.getSize()-1){
                System.out.print(" ");
                for(Square square : row){
                    System.out.print("****");
                }
            }

        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Grid clone = (Grid) super.clone();
        clone.grid = new Square[this.getSize()][this.getSize()];
        for(int row = 0; row < this.grid.length; row++){
            for(int col = 0; col < this.grid[0].length; col++)
                clone.grid[row][col] = (Square) this.grid[row][col].clone();
        }
        return clone;
    }
}