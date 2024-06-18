package homework;

public class MapPosition {
    public int row;
    public int column;
    MapPosition(int r, int c) {
        this.row = r;
        this.column = c;
    }
    public boolean equals(MapPosition rhs) {
        return this.row == rhs.row && this.column == rhs.column;
    }
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
