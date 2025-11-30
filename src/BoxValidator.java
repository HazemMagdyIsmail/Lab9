import java.util.*;

public class BoxValidator implements Validator {
    private final int[][] board;
    private final int boxIndex; // 0-based (0..8), left-to-right top-to-bottom

    public BoxValidator(int[][] board, int boxIndex) {
        this.board = board;
        this.boxIndex = boxIndex;
    }

    @Override
    public List<ValidationResult> validate() {
        int boxRow = (boxIndex / 3) * 3;
        int boxCol = (boxIndex % 3) * 3;
        Map<Integer, List<Integer>> positions = new HashMap<>();
        int pos = 1; // positions inside box are 1..9 left-to-right top-to-bottom

        for (int r = boxRow; r < boxRow + 3; ++r) {
            for (int c = boxCol; c < boxCol + 3; ++c) {
                int v = board[r][c];
                List<Integer> posList = positions.get(v);
                if (posList == null) {
                    posList = new ArrayList<>();
                    positions.put(v, posList);
                }
                posList.add(pos);
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

