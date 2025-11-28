
import java.io.*;
import java.util.*;

public class SudokuBoard {
    private int[][] grid = new int[9][9];

    public int[][] getGrid() { return grid; }

    public static SudokuBoard fromCSV(String path) throws Exception {
        SudokuBoard b = new SudokuBoard();
        BufferedReader br = new BufferedReader(new FileReader(path));
        for (int i = 0; i < 9; i++) {
            String[] parts = br.readLine().split(",");
            for (int j = 0; j < 9; j++) b.grid[i][j] = Integer.parseInt(parts[j]);
        }
        return b;
    }
}
