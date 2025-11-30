import java.util.*;

public class BoxValidator implements Validator {
    private final int[][] board;
    private final List<DuplicateInfo> dups;
    private final int boxIndex; // 0-based box index (0..8)

    public BoxValidator(int[][] board, List<DuplicateInfo> dups, int boxIndex) {
        this.board = board;
        this.dups = dups;
        this.boxIndex = boxIndex;
    }

    @Override
    public void run() {
        validate();
    }

    public void validate() {
        int boxRow = (boxIndex / 3) * 3;
        int boxCol = (boxIndex % 3) * 3;
        Map<Integer, List<Integer>> positions = new HashMap<>();
        int pos = 1;

        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                int val = board[r][c];
                if (val == 0) {
                    pos++;
                    continue;
                }
                positions.computeIfAbsent(val, k -> new ArrayList<>()).add(pos);
                pos++;
            }
        }

        for (Map.Entry<Integer, List<Integer>> entry : positions.entrySet()) {
            List<Integer> posList = entry.getValue();
            if (posList.size() > 1) {
                int[] posArray = posList.stream().mapToInt(Integer::intValue).toArray();
                synchronized (dups) {
                    dups.add(new DuplicateInfo("BOX", boxIndex + 1, entry.getKey(), posArray));
                }
            }
        }
    }
}
