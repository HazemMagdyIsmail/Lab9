
public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java Main <csv> <mode>");
            return;
        }

        String path = args[0];
        int mode = Integer.parseInt(args[1]);

        SudokuBoard board = SudokuBoard.fromCSV(path);
        SudokuVerifier verifier = new SudokuVerifier(board, mode);
        verifier.runValidation();
    }
}
