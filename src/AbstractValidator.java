
import java.util.*;

public abstract class AbstractValidator implements Validator {
    protected int[][] board;
    protected java.util.List<DuplicateInfo> out;

    public AbstractValidator(int[][] b, java.util.List<DuplicateInfo> o){
        board=b; out=o;
    }

    public void run(){ validate(); }
}
