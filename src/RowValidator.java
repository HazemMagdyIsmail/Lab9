import java.util.*;

public class RowValidator implements Validator {
    private final int[][] board;
    private final List<DuplicateInfo> dups;
    private final int row;  // 0-based row index

    public RowValidator(int[][] board, List<DuplicateInfo> dups, int row) {
        this.board = board;
        this.dups = dups;
        this.row = row;
    }

    @Override
    public void run() {
        validate();
    }

    public void validate() {
        Map<Integer, List<Integer>> positions = new HashMap<>();
        for (int c = 0; c < 9; c++) {
            int val = board[row][c];
            if (val == 0) continue; // assuming 0 means empty cell, ignore

            positions.computeIfAbsent(val, k -> new ArrayList<>()).add(c + 1);
        }

        for (Map.Entry<Integer, List<Integer>> entry : positions.entrySet()) {
            List<Integer> posList = entry.getValue();
            if (posList.size() > 1) {
                int[] posArray = posList.stream().mapToInt(Integer::intValue).toArray();
                synchronized (dups) {
                    dups.add(new DuplicateInfo("ROW", row + 1, entry.getKey(), posArray));
                }
            }
        }
    }
}
