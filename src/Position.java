import java.util.Comparator;

/**
 * Created by izasa on 2017-05-04.
 */

public class Position implements Comparable<Position>{


    int rowNum;
    int colNum;
    int distC;


    public Position(int rowNum, int colNum, int distC) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.distC = distC;
    }

    public void setDistC(int distC) {
        this.distC = distC;
    }

    public int getDistC() {
        return distC;
    }

    @Override
    public int compareTo(Position p) {
        return this.distC - p.distC;
    }
}
