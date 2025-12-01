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
        
        List<Thread> threads = new ArrayList<>();

        if (mode == 0) {
           
            for (int i = 0; i < 9; i++) {
                ValidatorFactory.createRowValidator(board, duplicates, i).run();
                ValidatorFactory.createColValidator(board, duplicates, i).run();
                ValidatorFactory.createBoxValidator(board, duplicates, i).run();
            }
        } else if (mode == 3) {
            
            Thread rowThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createRowValidator(board, duplicates, i).run();
            });
            Thread colThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createColValidator(board, duplicates, i).run();
            });
            Thread boxThread = new Thread(() -> {
                for (int i = 0; i < 9; i++)
                    ValidatorFactory.createBoxValidator(board, duplicates, i).run();
            });

            threads.add(rowThread);
            threads.add(colThread);
            threads.add(boxThread);

            for (Thread t : threads) t.start();
            for (Thread t : threads) t.join();

        } else if (mode == 27) {
            
            for (int i = 0; i < 9; i++) {
                threads.add(new Thread(ValidatorFactory.createRowValidator(board, duplicates, i)));
                threads.add(new Thread(ValidatorFactory.createColValidator(board, duplicates, i)));
                threads.add(new Thread(ValidatorFactory.createBoxValidator(board, duplicates, i)));
            }

            for (Thread t : threads) t.start();
            for (Thread t : threads) t.join();
        }

        return duplicates;
    }
}
