
import java.util.*;

public class ColumnValidator extends AbstractValidator {
    private int col;

    public ColumnValidator(int[][] board, List<DuplicateInfo> list, int colIndex){
        super(board,list);
        this.col=colIndex;
    }

    public void validate(){
        int[] freq=new int[10];
        for(int row=0;row<9;row++){
            int v=board[row][col];
            freq[v]++;
        }
        for(int v=1;v<=9;v++){
            if(freq[v]>1){
                out.add(new DuplicateInfo("COL",col+1,v,new int[]{1,2,3,4,5,6,7,8,9}));
            }
        }
    }
}
