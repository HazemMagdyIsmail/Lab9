import java.util.*;

public class RowValidator implements Validator {
    private final int[][] board;
    private final int row; // 0-based

    public RowValidator(int[][] board, int row) {
        this.board = board;
        this.row = row;
    }

    @Override
    public List<ValidationResult> call() {
        Map<Integer, List<Integer>> positions = new HashMap<>();
        for (int c = 0; c < 9; ++c) {
            int v = board[row][c];
            positions.computeIfAbsent(v, k -> new ArrayList<>()).add(c + 1); // columns 1-based
        }
        List<ValidationResult> res = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> e : positions.entrySet()) {
            if (e.getValue().size() > 1) {
                res.add(new ValidationResult(RegionType.ROW, row + 1, e.getKey(), e.getValue()));
            }
        }
        return res;
    }
}
