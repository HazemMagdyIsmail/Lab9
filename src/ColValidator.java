import java.util.*;

public class ColValidator implements Validator {
    private final int[][] board;
    private final int col; 

    public ColValidator(int[][] board, int col) {
        this.board = board;
        this.col = col;
    }

    @Override
    public List<ValidationResult> call() {
        Map<Integer, List<Integer>> positions = new HashMap<>();
        for (int r = 0; r < 9; ++r) {
            int v = board[r][col];
            positions.computeIfAbsent(v, k -> new ArrayList<>()).add(r + 1); 
        }
        List<ValidationResult> res = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> e : positions.entrySet()) {
            if (e.getValue().size() > 1) {
                res.add(new ValidationResult(RegionType.COL, col + 1, e.getKey(), e.getValue()));
            }
        }
        return res;
    }
}
