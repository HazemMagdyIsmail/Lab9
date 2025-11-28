
import java.util.*;

public class BoxValidator extends AbstractValidator {
    private int box;

    public BoxValidator(int[][] board, List<DuplicateInfo> list, int boxIndex){
        super(board,list);
        this.box=boxIndex;
    }

    public void validate(){
        int[] freq=new int[10];
        int r0=(box/3)*3;
        int c0=(box%3)*3;

        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                int v=board[r0+r][c0+c];
                freq[v]++;
            }
        }

        for(int v=1;v<=9;v++){
            if(freq[v]>1){
                out.add(new DuplicateInfo("BOX",box+1,v,new int[]{1,2,3,4,5,6,7,8,9}));
            }
        }
    }
}
