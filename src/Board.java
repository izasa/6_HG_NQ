import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by izasa on 2017-04-28.
 */
public class Board {

    private boolean[][] board;

    public Board(int N){
        this.board= new boolean[N][N];
    }


    public Board(Board board){
        for(int i=0; i<board.getBoard().length; i++){
            for(int j=0; j<board.getBoard().length; j++){
                this.board[i][j] = new Boolean(board.getBoard()[i][j]);
            }
        }
    }


    public void initBoard(){
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++){
                this.board[i][j] = new Boolean(false);
            }
        }
    }



    public boolean[][] getBoard() {
        return board;
    }



}
