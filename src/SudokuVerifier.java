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
        
        List<Thread> threads = new ArrayList<>();

        if (mode == 0) {
           
            for (int i = 0; i < 9; i++) {
                ValidatorFactory.createRowValidator(board, dups, i).run();
                ValidatorFactory.createColValidator(board, dups, i).run();
                ValidatorFactory.createBoxValidator(board, dups, i).run();
            }
        } else if (mode == 3) {
            
            Thread rowThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createRowValidator(board, dups, i).run();
            });
            Thread colThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createColValidator(board, dups, i).run();
            });
            Thread boxThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createBoxValidator(board, dups, i).run();
            });

            threads.add(rowThread);
            threads.add(colThread);
            threads.add(boxThread);

            for (Thread t : threads) t.start();
            for (Thread t : threads) t.join();
        } else if (mode == 27) {
            
            for (int i = 0; i < 9; i++) {
                threads.add(new Thread(ValidatorFactory.createRowValidator(board, dups, i)));
                threads.add(new Thread(ValidatorFactory.createColValidator(board, dups, i)));
                threads.add(new Thread(ValidatorFactory.createBoxValidator(board, dups, i)));
            }
            for (Thread t : threads) t.start();
            for (Thread t : threads) t.join();
        }

        if (dups.isEmpty()) {
            System.out.println("VALID");
        } else {
            System.out.println("INVALID");
            printDuplicates(dups, RegionType.ROW);
            System.out.println("------------------------------------------");
            printDuplicates(dups, RegionType.COL);
            System.out.println("------------------------------------------");
            printDuplicates(dups, RegionType.BOX);
        }
    }
private void printDuplicates(List<DuplicateInfo> dups, RegionType regionType) {
    for (DuplicateInfo d : dups) {
        if (d.getRegionType() == regionType) {  
            System.out.print(regionType.name() + " " + d.getIndex() + ", #" + d.getValue() + ", [");
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
