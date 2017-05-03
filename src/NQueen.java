import java.util.ArrayList;

/**
 * Created by izasa on 2017-04-28.
 */

public class NQueen {

    private int N;
    private boolean [] usedCol;
    private boolean [] usedRow;
    private Board board;
    private Boolean[][] boardT;
    private int nQueenCounter;

    public NQueen(Integer N) {
        this.N = N;
        this.board = new Board(N);
        this.usedCol = new boolean[N];
        this.usedRow = new boolean[N];
        boardT = new Boolean[N][N];
    }

    public void backtracking(Boolean[][] board ,int rowNum, int colNum, int queenOnBoard, Boolean [] usedRow, Boolean [] usedCol){
        Boolean [][] currentBoard = newBoard(board);
        currentBoard[rowNum][colNum] = true;
        Boolean [] currUsedColNum = copyTable(usedCol);
        Boolean [] currUsedRowNum = copyTable(usedRow);
        if(trueConstraints(rowNum,colNum,currentBoard,usedRow,usedCol)){
            nQueenCounter++;
            setConstraints(rowNum,colNum,currUsedRowNum,currUsedColNum);
        }else{
            currentBoard[rowNum][colNum] = false;
        }
        if(nQueenCounter==board.length){

        }
    }

    public Boolean[][] getBoardT() {
        return boardT;
    }

    public boolean trueConstraints(int rowNum, int colNum, Boolean [][] board, Boolean [] usedRow, Boolean [] usedCol){
        if(usedRow[rowNum]!=true && usedCol[colNum]!= true ){
            return false;
        }
        return true;
    }

    private Boolean[][] newBoard(Boolean [][] oldBoard){
        Boolean[][] newBoard = new Boolean[oldBoard.length][oldBoard.length];
        for(int i=0; i<oldBoard.length; i++){
            for(int j=0; j<oldBoard.length; j++){
                newBoard[i][j] = new Boolean(oldBoard[i][j]);
            }
        }
        return newBoard;
    }

    private Boolean[] copyTable(Boolean [] table){
        Boolean [] newTab = new Boolean[table.length];
        for(int i=0; i<table.length; i++){
            newTab[i] = new Boolean(table[i]);
        }
        return newTab;
    }

    private void setConstraints(int rowNum, int colNum,  Boolean [] usedRow, Boolean [] usedCol){
        usedCol[colNum] = true;
        usedRow[rowNum] = true;
    }
}
