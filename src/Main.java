public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java Main <csv filepath> <mode>");
            return;
        }

        String path = args[0];        // use user-provided CSV
        int mode = Integer.parseInt(args[1]);

        SudokuBoard board = SudokuBoard.fromCSV(path);   // <-- FIXED
        SudokuVerifier verifier = new SudokuVerifier(board, mode);
        verifier.runValidation();
    }
}
