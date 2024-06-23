package homework;

/**
 * Represents a position on a map with row and column coordinates.
 */
public class MapPosition {

    public int row;     // Row coordinate
    public int column;  // Column coordinate

    /**
     * Constructor to initialize a MapPosition object with given row and column coordinates.
     * @param r The row coordinate.
     * @param c The column coordinate.
     */
    MapPosition(int r, int c) {
        this.row = r;       // Initialize row
        this.column = c;    // Initialize column
    }

    /**
     * Checks if this MapPosition is equal to another MapPosition.
     * @param rhs The other MapPosition to compare with.
     * @return true if both have the same row and column coordinates, false otherwise.
     */
    public boolean equals(MapPosition rhs) {
        return this.row == rhs.row && this.column == rhs.column;
    }

    /**
     * Returns a string representation of this MapPosition.
     * @return A string in the format "(row, column)" representing the coordinates.
     */
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
