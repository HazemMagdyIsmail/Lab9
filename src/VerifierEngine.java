import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerifierEngine {
    private final SudokuBoard board;

    public VerifierEngine(SudokuBoard board) {
        this.board = board;
    }

    public List<DuplicateInfo> verify(int mode) throws InterruptedException {
        List<DuplicateInfo> duplicates = Collections.synchronizedList(new ArrayList<>());
        int[][] grid = board.getGrid();
        List<Thread> threads = new ArrayList<>();

        if (mode == 0) {
            // Sequential validation
            for (int i = 0; i < 9; i++) {
                ValidatorFactory.createRowValidator(grid, duplicates, i).run();
                ValidatorFactory.createColValidator(grid, duplicates, i).run();
                ValidatorFactory.createBoxValidator(grid, duplicates, i).run();
            }
        } else if (mode == 3) {
            // Three threads: one for rows, one for cols, one for boxes
            Thread rowThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createRowValidator(grid, duplicates, i).run();
            });
            Thread colThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createColValidator(grid, duplicates, i).run();
            });
            Thread boxThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createBoxValidator(grid, duplicates, i).run();
            });

            threads.add(rowThread);
            threads.add(colThread);
            threads.add(boxThread);

            for (Thread t : threads) t.start();
            for (Thread t : threads) t.join();

        } else if (mode == 27) {
            // 27 threads: one per row, col, box validator
            for (int i = 0; i < 9; i++) {
                threads.add(new Thread(ValidatorFactory.createRowValidator(grid, duplicates, i)));
                threads.add(new Thread(ValidatorFactory.createColValidator(grid, duplicates, i)));
                threads.add(new Thread(ValidatorFactory.createBoxValidator(grid, duplicates, i)));
            }

            for (Thread t : threads) t.start();
            for (Thread t : threads) t.join();
        }

        return duplicates;
    }
}
