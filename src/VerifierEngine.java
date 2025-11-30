import java.util.*;

public class VerifierEngine {
    private final int[][] board;

    public VerifierEngine(int[][] board) {
        this.board = board;
    }

    public List<ValidationResult> verifySequential() {
        List<ValidationResult> all = new ArrayList<>();

        // Validate rows
        for (int r = 0; r < 9; ++r) {
            Validator validator = new RowValidator(board, r);
            List<ValidationResult> results = validator.validate();
            if (results != null) {
                all.addAll(results);
            }
        }

        // Validate columns
        for (int c = 0; c < 9; ++c) {
            Validator validator = new ColValidator(board, c);
            List<ValidationResult> results = validator.validate();
            if (results != null) {
                all.addAll(results);
            }
        }

        // Validate boxes
        for (int b = 0; b < 9; ++b) {
            Validator validator = new BoxValidator(board, b);
            List<ValidationResult> results = validator.validate();
            if (results != null) {
                all.addAll(results);
            }
        }

        return all;
    }
}
