

public class ValidatorFactory {
    // Creates validators for rows, cols, boxes
    public static Validator  createRowValidator(int[][] board, int rowIndex) {
        return new RowValidator(board, rowIndex);
    }

    public static Validator createColValidator(int[][] board, int colIndex) {
        return new ColValidator(board, colIndex);
    }

    public static Validator createBoxValidator(int[][] board, int boxIndex) {
        return new BoxValidator(board, boxIndex);
    }
}
