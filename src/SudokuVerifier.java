
import java.util.*;

public class SudokuVerifier {
    private SudokuBoard board;
    private int mode;

    public SudokuVerifier(SudokuBoard b, int m){
        board=b; mode=m;
    }

    public void runValidation() throws Exception {
        List<DuplicateInfo> dups = Collections.synchronizedList(new ArrayList<>());

        int[][] g = board.getGrid();

        List<Thread> threads = new ArrayList<>();

        if(mode==0){
            for(int i=0;i<9;i++) new RowValidator(g,dups,i).validate();
            for(int i=0;i<9;i++) new ColumnValidator(g,dups,i).validate();
            for(int i=0;i<9;i++) new BoxValidator(g,dups,i).validate();
        }
        else if(mode==3){
            Thread t1=new Thread(()->{
                for(int i=0;i<9;i++) new RowValidator(g,dups,i).validate();
            });
            Thread t2=new Thread(()->{
                for(int i=0;i<9;i++) new ColumnValidator(g,dups,i).validate();
            });
            Thread t3=new Thread(()->{
                for(int i=0;i<9;i++) new BoxValidator(g,dups,i).validate();
            });
            threads.add(t1); threads.add(t2); threads.add(t3);
            for(Thread t:threads)t.start();
            for(Thread t:threads)t.join();
        }
        else if(mode==27){
            for(int i=0;i<9;i++) threads.add(new Thread(new RowValidator(g,dups,i)));
            for(int i=0;i<9;i++) threads.add(new Thread(new ColumnValidator(g,dups,i)));
            for(int i=0;i<9;i++) threads.add(new Thread(new BoxValidator(g,dups,i)));

            for(Thread t:threads)t.start();
            for(Thread t:threads)t.join();
        }

        if(dups.isEmpty()){
            System.out.println("VALID");
        } else {
            System.out.println("INVALID");
            print(dups,"ROW");
            System.out.println("------------------------------------------");
            print(dups,"COL");
            System.out.println("------------------------------------------");
            print(dups,"BOX");
        }
    }

  private void print(List<DuplicateInfo> list, String type) {
    for (DuplicateInfo d : list) {
        if (d.type.equals(type)) {
            System.out.print(type + " " + d.index + ", #" + d.value + ", [");
            for (int i = 0; i < d.positions.length; i++) {
                System.out.print(d.positions[i]);
                if (i < d.positions.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
}


}
