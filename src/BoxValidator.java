import java.util.*;

public class BoxValidator implements Validator {
    private final int[][] board;
    private final int boxIndex; // 0-based (0..8), left-to-right top-to-bottom

    public BoxValidator(int[][] board, int boxIndex) {
        this.board = board;
        this.boxIndex = boxIndex;
    }

    @Override
    public List<ValidationResult> call() {
        int boxRow = (boxIndex / 3) * 3;
        int boxCol = (boxIndex % 3) * 3;
        Map<Integer, List<Integer>> positions = new HashMap<>();
        // positions inside box are 1..9 left-to-right top-to-bottom
        int pos = 1;
        for (int r = boxRow; r < boxRow + 3; ++r) {
            for (int c = boxCol; c < boxCol + 3; ++c) {
                int v = board[r][c];
                positions.computeIfAbsent(v, k -> new ArrayList<>()).add(pos);
                pos++;
            }
        }
        List<ValidationResult> res = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> e : positions.entrySet()) {
            if (e.getValue().size() > 1) {
                res.add(new ValidationResult(RegionType.BOX, boxIndex + 1, e.getKey(), e.getValue()));
            }
        }
        return res;
    }
}
