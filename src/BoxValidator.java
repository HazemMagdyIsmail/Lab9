
import java.util.ArrayList;
import java.util.List;

public class BoxValidator extends AbstractValidator {
    private int boxIndex;

    public BoxValidator(int[][] board, List<DuplicateInfo> list, int boxIndex) {
        super(board, list);
        this.boxIndex = boxIndex;
    }

    @Override
    public void validate() {
        int[] freq = new int[10];

        // Calculate starting row and column of the box
        int boxRowStart = (boxIndex / 3) * 3;
        int boxColStart = (boxIndex % 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int v = board[boxRowStart + i][boxColStart + j];
                freq[v]++;
            }
        }

        for (int v = 1; v <= 9; v++) {
            if (freq[v] > 1) {
                List<Integer> duplicatePositions = new ArrayList<>();
                int positionCounter = 1; // positions from 1 to 9 within the box

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[boxRowStart + i][boxColStart + j] == v) {
                            duplicatePositions.add(positionCounter);
                        }
                        positionCounter++;
                    }
                }

                int[] positions = duplicatePositions.stream().mapToInt(Integer::intValue).toArray();
                out.add(new DuplicateInfo("BOX", boxIndex + 1, v, positions));
            }
        }
    }
}
