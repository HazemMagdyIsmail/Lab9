import java.util.*;

public class ColValidator implements Validator  {
    private final int[][] board;
    private final List<DuplicateInfo> dups;
    private final int col;  // 0-based

    public ColValidator(int[][] board, List<DuplicateInfo> dups, int col) {
        this.board = board;
        this.dups = dups;
        this.col = col;
    }

    @Override
    public void run() {
        validate();
    }

    public void validate() {
        Map<Integer, List<Integer>> positions = new HashMap<>();
        for (int r = 0; r < 9; r++) {
            int val = board[r][col];
            if (val == 0) continue;
            positions.computeIfAbsent(val, k -> new ArrayList<>()).add(r + 1);
        }

        for (Map.Entry<Integer, List<Integer>> entry : positions.entrySet()) {
            List<Integer> posList = entry.getValue();
            if (posList.size() > 1) {
                int[] posArray = posList.stream().mapToInt(Integer::intValue).toArray();
                synchronized (dups) {
                    dups.add(new DuplicateInfo("COL", col + 1, entry.getKey(), posArray));
                }
            }
        }
    }
}
