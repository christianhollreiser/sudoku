package generator;

public class Position {
    public int row;
    public int col;

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position)){
            return false;
        }
        Position ip = (Position) obj;
        return ip.row == this.row && ip.col == this.col;
    }

    @Override
    public int hashCode() {
        return 31*this.row + this.col;
    }

    @Override
    public String toString() {
        return "(" + this.row + ", " + this.col + ")";
    }
}