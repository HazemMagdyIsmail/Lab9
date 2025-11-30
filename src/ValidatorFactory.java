import java.util.List;

public class ValidatorFactory {
    public static Validator createRowValidator(SudokuBoard board, List<DuplicateInfo> dups, int row) {
        return new RowValidator(board, dups, row);
    }

    public static Validator createColValidator(SudokuBoard board, List<DuplicateInfo> dups, int col) {
        return new ColValidator(board, dups, col);
    }

    public static Validator createBoxValidator(SudokuBoard board, List<DuplicateInfo> dups, int box) {
        return new BoxValidator(board, dups, box);
    }
}
