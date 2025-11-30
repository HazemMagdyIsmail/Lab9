import java.io.IOException;

public class SudokuBoard {
    private final int[][] grid;

    public SudokuBoard(String path) throws IOException {
        this.grid = CSVReader.readBoard(path); // Your CSV reader code
    }

    public int[][] getGrid() {
        return grid;
    }
}
