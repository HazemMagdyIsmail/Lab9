import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSVReader {
    // Reads a 9x9 board from the given file. Accepts CSV or space-separated or mixed.
    public static int[][] readBoard(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        List<int[]> rows = new ArrayList<>();
        for (String raw : lines) {
            if (raw.trim().isEmpty()) continue;
            // split by commas or whitespace
            String[] tokens = raw.trim().split("[,\\s]+");
            if (tokens.length != 9) {
                throw new IOException("Each row must have 9 numbers. Offending line: " + raw);
            }
            int[] row = new int[9];
            for (int i = 0; i < 9; ++i) {
                row[i] = Integer.parseInt(tokens[i]);
            }
            rows.add(row);
        }
        if (rows.size() != 9) throw new IOException("File must contain 9 rows");
        int[][] board = new int[9][9];
        for (int i = 0; i < 9; ++i) board[i] = rows.get(i);
        return board;
    }
}
