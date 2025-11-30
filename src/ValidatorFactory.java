import java.util.List;

public class ValidatorFactory {
    public static Validator createRowValidator(int[][] board, List<DuplicateInfo> dups, int row) {
        return new RowValidator(board, dups, row);
    }

    public static Validator createColValidator(int[][] board, List<DuplicateInfo> dups, int col) {
        return new ColValidator(board, dups, col);
    }

    public static Validator createBoxValidator(int[][] board, List<DuplicateInfo> dups, int box) {
        return new BoxValidator(board, dups, box);
    }
}
