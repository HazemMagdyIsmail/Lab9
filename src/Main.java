import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Main <csv-file> <mode>");
            System.err.println("Mode must be: 0, 3, or 27");
            return;
        }

        String path = args[0];
        int mode;

        try {
            mode = Integer.parseInt(args[1]);
            if (mode != 0 && mode != 3 && mode != 27) {
                System.err.println("Mode must be 0, 3, or 27");
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Mode must be a number (0, 3, or 27)");
            return;
        }


        SudokuBoard board;
        try {
            board = new SudokuBoard(path);
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            return;
        }

        SudokuVerifier verifier = new SudokuVerifier(board, mode);

        try {
            verifier.runValidation();
        } catch (Exception e) {
            System.err.println("Validation error: " + e.getMessage());
        }
    }
}
