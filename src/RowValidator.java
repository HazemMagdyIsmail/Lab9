
import java.util.*;

public class RowValidator extends AbstractValidator {
    private int row;

    public RowValidator(int[][] board, List<DuplicateInfo> list, int rowIndex){
        super(board,list);
        this.row=rowIndex;
    }

    public void validate(){
        int[] freq=new int[10];
        for(int col=0;col<9;col++){
            int v=board[row][col];
            freq[v]++;
        }
        for(int v=1;v<=9;v++){
            if(freq[v]>1){
                out.add(new DuplicateInfo("ROW",row+1,v,new int[]{1,2,3,4,5,6,7,8,9}));
            }
        }
    }
}
