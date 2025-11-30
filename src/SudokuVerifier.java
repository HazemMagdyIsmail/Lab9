import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuVerifier {
    private final SudokuBoard board;
    private final int mode;

    public SudokuVerifier(SudokuBoard board, int mode) {
        this.board = board;
        this.mode = mode;
    }

    public void runValidation() throws InterruptedException {
        List<DuplicateInfo> dups = Collections.synchronizedList(new ArrayList<>());
        int[][] grid = board.getGrid();
        List<Thread> threads = new ArrayList<>();

        if (mode == 0) {
            // Sequential run on main thread
            for (int i = 0; i < 9; i++) {
                ValidatorFactory.createRowValidator(grid, dups, i).run();
                ValidatorFactory.createColValidator(grid, dups, i).run();
                ValidatorFactory.createBoxValidator(grid, dups, i).run();
            }
        } else if (mode == 3) {
            // 3 threads: one for rows, one for cols, one for boxes
            Thread rowThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createRowValidator(grid, dups, i).run();
            });
            Thread colThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createColValidator(grid, dups, i).run();
            });
            Thread boxThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createBoxValidator(grid, dups, i).run();
            });

            threads.add(rowThread);
            threads.add(colThread);
            threads.add(boxThread);

            for (Thread t : threads) t.start();
            for (Thread t : threads) t.join();
        } else if (mode == 27) {
            // 27 threads: one per each row/col/box validator
            for (int i = 0; i < 9; i++) {
                threads.add(new Thread(ValidatorFactory.createRowValidator(grid, dups, i)));
                threads.add(new Thread(ValidatorFactory.createColValidator(grid, dups, i)));
                threads.add(new Thread(ValidatorFactory.createBoxValidator(grid, dups, i)));
            }
            for (Thread t : threads) t.start();
            for (Thread t : threads) t.join();
        }

        if (dups.isEmpty()) {
            System.out.println("VALID");
        } else {
            System.out.println("INVALID");
            printDuplicates(dups, "ROW");
            System.out.println("------------------------------------------");
            printDuplicates(dups, "COL");
            System.out.println("------------------------------------------");
            printDuplicates(dups, "BOX");
        }
    }

  private void printDuplicates(List<DuplicateInfo> dups, String type) {
    for (DuplicateInfo d : dups) {
        if (d.getType().equals(type)) {
            System.out.print(type + " " + d.getIndex() + ", #" + d.getValue() + ", [");
            int[] positions = d.getPositions();
            for (int i = 0; i < positions.length; i++) {
                System.out.print(positions[i]);
                if (i < positions.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
}

}
