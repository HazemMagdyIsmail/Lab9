
import java.util.*;

public class RowValidator extends AbstractValidator {
    private int row;

    public RowValidator(int[][] board, List<DuplicateInfo> list, int rowIndex){
        super(board,list);
        this.row=rowIndex;
    }

   public void validate() {
    int[] freq = new int[10];
    for (int col = 0; col < 9; col++) {
        int v = board[row][col];
        freq[v]++;
    }
    for (int v = 1; v <= 9; v++) {
        if (freq[v] > 1) {
            List<Integer> duplicatePositions = new ArrayList<>();
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == v) {
                    duplicatePositions.add(col + 1); // 1-based position in the row
                }
            }
            int[] positions = duplicatePositions.stream().mapToInt(Integer::intValue).toArray();
            out.add(new DuplicateInfo("ROW", row + 1, v, positions));
        }
    }
}

}
