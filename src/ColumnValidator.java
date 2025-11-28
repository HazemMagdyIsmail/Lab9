
import java.util.ArrayList;
import java.util.List;


public class ColumnValidator extends AbstractValidator {
    private int col;

    public ColumnValidator(int[][] board, List<DuplicateInfo> list, int colIndex) {
        super(board, list);
        this.col = colIndex;
    }

    public void validate() {
        int[] freq = new int[10];
        for (int row = 0; row < 9; row++) {
            int v = board[row][col];
            freq[v]++;
        }
        for (int v = 1; v <= 9; v++) {
            if (freq[v] > 1) {
                List<Integer> duplicatePositions = new ArrayList<>();
                for (int row = 0; row < 9; row++) {
                    if (board[row][col] == v) {
                        duplicatePositions.add(row + 1); // 1-based row index
                    }
                }
                int[] positions = duplicatePositions.stream().mapToInt(Integer::intValue).toArray();
                out.add(new DuplicateInfo("COL", col + 1, v, positions));
            }
        }
    }
}
