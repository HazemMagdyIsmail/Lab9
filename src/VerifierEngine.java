import java.util.*;
import java.util.concurrent.*;

public class VerifierEngine {
    private final int[][] board;

    public VerifierEngine(int[][] board) {
        this.board = board;
    }

    // Sequential: check all rows, cols, boxes in the main thread
    public List<ValidationResult> verifySequential() {
        List<ValidationResult> all = new ArrayList<>();
        // rows
        for (int r = 0; r < 9; ++r) {
            try {
                all.addAll(new RowValidator(board, r).call());
            } catch (Exception ignored) {}
        }
        // cols
        for (int c = 0; c < 9; ++c) {
            try {
                all.addAll(new ColValidator(board, c).call());
            } catch (Exception ignored) {}
        }
        // boxes
        for (int b = 0; b < 9; ++b) {
            try {
                all.addAll(new BoxValidator(board, b).call());
            } catch (Exception ignored) {}
        }
        return all;
    }

    // 3-mode: main + 3 threads (rows, cols, boxes) each thread validates all of its regions
    public List<ValidationResult> verifyThreeThreads() throws InterruptedException, ExecutionException {
        ExecutorService ex = Executors.newFixedThreadPool(3);
        List<Callable<List<ValidationResult>>> tasks = new ArrayList<>();
        // one task for all rows
        tasks.add(() -> {
            List<ValidationResult> list = new ArrayList<>();
            for (int r = 0; r < 9; ++r) list.addAll(new RowValidator(board, r).call());
            return list;
        });
        // one task for all cols
        tasks.add(() -> {
            List<ValidationResult> list = new ArrayList<>();
            for (int c = 0; c < 9; ++c) list.addAll(new ColValidator(board, c).call());
            return list;
        });
        // one task for all boxes
        tasks.add(() -> {
            List<ValidationResult> list = new ArrayList<>();
            for (int b = 0; b < 9; ++b) list.addAll(new BoxValidator(board, b).call());
            return list;
        });

        List<Future<List<ValidationResult>>> futures = ex.invokeAll(tasks);
        List<ValidationResult> combined = new ArrayList<>();
        for (Future<List<ValidationResult>> f : futures) {
            combined.addAll(f.get());
        }
        ex.shutdown();
        return combined;
    }

    // 27-mode: main + 27 threads (one thread per each row/col/box)
    public List<ValidationResult> verifyTwentySevenThreads() throws InterruptedException, ExecutionException {
        int totalThreads = 27;
        ExecutorService ex = Executors.newFixedThreadPool(totalThreads);
        List<Callable<List<ValidationResult>>> tasks = new ArrayList<>();

        // 9 row tasks
        for (int r = 0; r < 9; ++r) tasks.add(new RowValidator(board, r));
        // 9 col tasks
        for (int c = 0; c < 9; ++c) tasks.add(new ColValidator(board, c));
        // 9 box tasks
        for (int b = 0; b < 9; ++b) tasks.add(new BoxValidator(board, b));

        List<Future<List<ValidationResult>>> futures = ex.invokeAll(tasks);
        List<ValidationResult> combined = new ArrayList<>();
        for (Future<List<ValidationResult>> f : futures) {
            combined.addAll(f.get());
        }
        ex.shutdown();
        return combined;
    }
}
