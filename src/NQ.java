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
    private Boolean [][] board;

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
        Boolean [] currLeft = copyDiag(diagLeft);
        Boolean [] currRight = copyDiag(diagRight);
        Boolean [] currCols = copyTable(cols);
        if(rowNum<N){
            for(int i=0; i<N; i++){// iteruje po kolumnach
                if(setQueenIfPosssible(rowNum,i, currCols, currRight,currLeft)){
                    simpleBacktracking(rowNum+1,currLeft,currRight,currCols);
                    if(rowNum==N-1){
                        solutions++;
                    }
                    removeConstraints(rowNum,i, currCols, currRight,currLeft);
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
            ArrayList tmp = new ArrayList();
            for(int j=0;j<N; j++){
                tmp.add(j);
            }
            domains.add(tmp);
        }
    }

    public void initBoards(){
        board = new Boolean[N][N];
        for(int i=0; i<N;i++){
            for(int j=0;j<N; j++){
                board[i][j] = new Boolean(false);
            }
        }
    }

    public boolean setQueenIfPosssible(int rownum, int colnum, Boolean [] cols, Boolean [] diagRight, Boolean [] diagLeft){
        if( cols[colnum]==false && diagRight[rownum+colnum]==false && diagLeft[N-1+colnum-rownum]==false){
            cols[colnum]=true;
            diagRight[rownum+colnum]=true;
            diagLeft[N-1+colnum-rownum]=true;
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


    public void runFrowardcheckingB(){
        initBoards();
        forwardchecking(0, diagLeft,diagRight,cols,board);
    }


    public void forwardchecking(int rowNum, Boolean[] diagLeft, Boolean []diagRight, Boolean [] cols, Boolean [][] domains){
        Boolean [] currLeft = copyDiag(diagLeft);
        Boolean [] currRight = copyDiag(diagRight);
        Boolean [] currCols = copyTable(cols);
        Boolean[][] currDom = domains;
        if(rowNum<N){
                for (int j = 0; j < currDom.length; j++) { // ust ze zajete miejsce
                    if (currDom[rowNum][j] == false) { // jezeli dane pole jest w domenie
                        currCols[j] = true;
                        currRight[rowNum + j] = true;
                        currLeft[N - 1 + j - rowNum] = true;
                        currDom[rowNum][j] = true;
                        if (rowNum + 1 == N) {
                            solutions++;
                        } else {
                            forwardchecking(rowNum + 1, currLeft, currRight, currCols, setNewDomainsB(currDom, currCols, currRight, currLeft));

                        }
                        currDom[rowNum][j] = false;
                        currCols[j] = false;
                        currRight[rowNum + j] = false;
                        currLeft[N - 1 + j - rowNum] = false;
                    }
                }

        }
    }


    public ArrayList<ArrayList<Integer>> copyDomains(ArrayList<ArrayList<Integer>> dom){
        ArrayList<ArrayList<Integer>> newDom = new ArrayList<>();
        for(int i=0; i<N; i++){
            ArrayList tmp = new ArrayList();
            for(int j=0; j<dom.get(i).size(); j++){
                tmp.add(new Integer(dom.get(i).get(j)));
            }
            newDom.add(tmp);
        }
        return newDom;
    }


    public Boolean [][] setNewDomainsB(Boolean[][] oldDom, Boolean [] cols, Boolean [] diagRight, Boolean [] diagLeft){
        Boolean [][] newB = new Boolean[N][N];
        for(int i=0; i<N; i++){//iteracja po wierszach
            for(int j=0; j<N; j++){ //iter po kolumnach
                if(oldDom[i][j] ||(cols[j] || diagRight[i+j] ||
                        diagLeft[N-1+j-i])){
                    newB[i][j] = new Boolean(true);
                }else{
                    newB[i][j] = new Boolean(false);
                }
            }

        }
        return newB;
    }


}
