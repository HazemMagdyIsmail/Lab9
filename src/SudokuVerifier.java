import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class SudokuVerifier {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java -jar SudokuVerifier.jar <csv-file> <mode>");
            System.err.println("mode: 0 | 3 | 27");
            System.exit(1);
        }

        String path = args[0];
        String modeStr = args[1];
        int mode;
        try {
            mode = Integer.parseInt(modeStr);
            if (mode != 0 && mode != 3 && mode != 27) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.err.println("Mode must be 0, 3, or 27");
            return;
        }

        int[][] board;
        try {
            board = CSVReader.readBoard(path);
        } catch (IOException ex) {
            System.err.println("Failed to read file: " + ex.getMessage());
            return;
        }

        VerifierEngine engine = new VerifierEngine(board);
        List<ValidationResult> results;
        try {
            if (mode == 0) results = engine.verifySequential();
            else if (mode == 3) results = engine.verifyThreeThreads();
            else results = engine.verifyTwentySevenThreads();
        } catch (InterruptedException | ExecutionException ex) {
            System.err.println("Verification error: " + ex.getMessage());
            return;
        }

        if (results.isEmpty()) {
            System.out.println("VALID");
        } else {
            System.out.println("INVALID");
            // Group by type for neat printing: ROWS, COLS, BOXES
            printGrouped(results);
        }
    }

    private static void printGrouped(List<ValidationResult> results) {
    // Print rows
    System.out.println("Row Duplicates:");
    for (int i = 1; i <= 9; i++) {
        for (ValidationResult vr : results) {
            if (vr.getRegionType() == RegionType.ROW && vr.getRegionIndex() == i) {
                System.out.println(vr.formatForOutput());
            }
        }
    }
    System.out.println("------------------------------------------");

    // Print columns
    System.out.println("Column Duplicates:");
    for (int i = 1; i <= 9; i++) {
        for (ValidationResult vr : results) {
            if (vr.getRegionType() == RegionType.COL && vr.getRegionIndex() == i) {
                System.out.println(vr.formatForOutput());
            }
        }
    }
    System.out.println("------------------------------------------");

    // Print boxes
    System.out.println("Box Duplicates:");
    for (int i = 1; i <= 9; i++) {
        for (ValidationResult vr : results) {
            if (vr.getRegionType() == RegionType.BOX && vr.getRegionIndex() == i) {
                System.out.println(vr.formatForOutput());
            }
        }
    }
}

}
