import java.util.ArrayList;

/**
 * Created by izasa on 2017-05-03.
 */
public class NQ
{
    private int N;
    private Boolean [] diagLeft;
    private Boolean [] diagRight;
    private Boolean [] cols;
    private int solutions =0;
    private ArrayList<ArrayList<Integer>> domains = new ArrayList<>();

    public NQ(int n) {
        this.N = n;
        initTables();
    }


    public int getSolutions() {
        return solutions;
    }

    public void runsSimpleBacktracjking(){
        simpleBacktracking(0,diagLeft,diagRight,cols);
    }

    public void simpleBacktracking(int rowNum, Boolean[] diagLeft, Boolean []diagRight, Boolean [] cols){
        //System.out.println("sb");
        Boolean [] currLeft = copyDiag(diagLeft);
        Boolean [] currRight = copyDiag(diagRight);
        Boolean [] currCols = copyTable(cols);
        if(rowNum<N){
            for(int i=0; i<N; i++){// iteruje po kolumnach
                if(setQueenIfPosssible(rowNum,i, currCols, currRight,currLeft)){
                    //System.out.println("dodano");
                    simpleBacktracking(rowNum+1,currLeft,currRight,currCols);
                    if(rowNum==N-1){
                        //System.out.println("rozw");
                        solutions++;
                    }
                    removeConstraints(rowNum,i, currCols, currRight,currLeft);
                }else{
                   // System.out.println("nie dodano");
                }
            }
        }
    }

    public void initTables(){
        diagLeft = new Boolean[2*N-1];
        diagRight = new Boolean[2*N-1];
        cols = new Boolean[N];
        for(int i=0; i<N; i++){
            cols[i]=false;
        }
        for(int i=0; i<2*N-1; i++){
            diagRight[i] = false;
            diagLeft[i]= false;
        }
    }

    public void initDomains(){
        for(int i=0; i<N;i++){
            domains.add(new ArrayList<Integer>());
            ArrayList tmp = new ArrayList();
            for(int j=0;j<N; j++){
                tmp.add(j);
            }
            domains.add(tmp);
        }
    }

    public boolean setQueenIfPosssible(int rownum, int colnum, Boolean [] cols, Boolean [] diagRight, Boolean [] diagLeft){
        //System.out.println("colnum: "+colnum+" rownum: "+rownum+ " cols[colnum]: "+cols[colnum]+ " diagRight[rownum+colnum] "+diagRight[rownum+colnum] + " diagLeft[N-1+colnum-rownum] "+diagLeft[N-1+colnum-rownum]);
        if( cols[colnum]==false && diagRight[rownum+colnum]==false && diagLeft[N-1+colnum-rownum]==false){
            cols[colnum]=true;
            diagRight[rownum+colnum]=true;
            diagLeft[N-1+colnum-rownum]=true;
            //System.out.println("true");
            return true;
        }
        return false;
    }

    public Boolean [] copyTable(Boolean [] table){
        Boolean [] newTable = new Boolean [N];
        for(int i=0; i<N; i++){
            newTable[i]= new Boolean(table[i]);
        }
        return newTable;
    }

    public Boolean [] copyDiag(Boolean [] table){
        Boolean [] newTable = new Boolean [2*N-1];
        for(int i=0; i<2*N-1; i++){
            newTable[i]= new Boolean(table[i]);
        }
        return newTable;
    }


    public void removeConstraints(int rownum, int colnum, Boolean [] cols, Boolean [] diagRight, Boolean [] diagLeft){
        cols[colnum]=false;
        diagRight[rownum+colnum]=false;
        diagLeft[N-1+colnum-rownum]=false;
    }

    public void runFrowardchecking(){
        initDomains();

    }

    public void forwardchecking(int rowNum){

    }
}
