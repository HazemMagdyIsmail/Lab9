import java.util.*;

public class RowValidator implements Validator {
    private final SudokuBoard board;
    private final List<DuplicateInfo> dups;
    private final int row;

    public RowValidator(SudokuBoard board, List<DuplicateInfo> dups, int row) {
        this.board = board;
        this.dups = dups;
        this.row = row;
    }

    @Override
    public void run() {
        validate();
    }

    public void validate() {
        int[][] grid = board.getGrid();
        Map<Integer, List<Integer>> positions = new HashMap<>();
        for (int c = 0; c < 9; c++) {
            int val = grid[row][c];
            if (val == 0) continue;
            positions.computeIfAbsent(val, k -> new ArrayList<>()).add(c + 1);
        }

        for (Map.Entry<Integer, List<Integer>> entry : positions.entrySet()) {
            List<Integer> posList = entry.getValue();
            if (posList.size() > 1) {
                int[] posArray = posList.stream().mapToInt(Integer::intValue).toArray();
                synchronized (dups) {
                    dups.add(new DuplicateInfo(RegionType.ROW, row + 1, entry.getKey(), posArray));
                }
            }
        }
    }
}
