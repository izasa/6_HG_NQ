import java.util.*;


/**
 * Created by izasa on 2017-03-31.
 */
public class CSP {

    private Graph graph;
    public Graph graphF;
    private HashMap<Integer,Integer> colorNumbers = new HashMap<>();// zbior <kolor,ilosc wystapien>
    private ArrayList<Integer> colors = new ArrayList<>();
     ArrayList<Position> positions = new ArrayList<>();
    private int N;
    public int btNum =0;
    public int fcNum =0;


    public CSP(Graph graph, int N) {
        this.graph = graph;
        this.N=N;
        initColors();
        initPositions();
    }


    public void initPositions(){
        float center = (N-1)/2.f;
        //positions.clear();
        double floorX;
        double floorY;
        for (int row = 0; row<N; ++row) {
            for (int col = 0; col < N; ++col){
                floorX = new Double(0.0);
                floorY = new Double(0.0);
                floorX = Math.floor(Math.abs(center - col));
                int xDist = (int) floorX;
                floorY = Math.floor(Math.abs(center - row));
                int yDist = (int) floorY;

                int dist = xDist + yDist;
                positions.add(new Position(row,col,dist));
            }
        }
        Collections.sort(positions);
    }


    public void initColors(){
        int colorsSetSize =  N%2==0 ? 2*N : (2*N)+1;
        for(int i =0; i<colorsSetSize; i++){
            colorNumbers.put(i,0);
        }
        for(int i=0; i<colorsSetSize; i++){
            colors.add(i);
        }
    }

/*
    public boolean simpleGoThroughGraphBacktracking(int rowNum, int colNum,int oldColor){
        boolean success = false;
        int value = oldColor;
        ArrayList<ColorPair> pairs = new ArrayList<>();
        while(value<colors.size() && !success){
            graph.setNodeColor(rowNum,colNum,value);
            if(checkContraints(graph,rowNum,colNum,value)){
                pairs = addUsedColorPair(graph,rowNum,colNum,value);
                success = true;
            }
            else{
                value++;
                removeColorsPairs(pairs,graph);
                pairs.clear();
            }
        }
        if(!success){
            graph.setNodeColor(rowNum,colNum,-1);
            removeColorsPairs(pairs,graph);
            return false;// brak możliwości ustawienia koloru
        }
        if(rowNum == N-1 && colNum==N-1){
           return true; // koniec grafu
        }
        if(colNum==N-1){
            colNum=-1;
            rowNum++;
        }
        colNum++;
        success = simpleGoThroughGraphBacktracking(rowNum,colNum,0);
        if(!success){
            return simpleGoThroughGraphBacktracking(rowNum,colNum,value+1);
        }else{
            return true;
        }
    }
*/

    public void simpleGoThroughGraphBacktrackingAll(Graph g, int rowNum, int colNum){
        Graph currentGraph = new Graph(g,g.getDomains());
        int tmpColNum =colNum;
        int tmpRowNum=rowNum;
        if(colNum==N-1){
            tmpColNum=-1;
            tmpRowNum = rowNum+1;
        }
        tmpColNum++;
        for(Integer col: colors){
            ArrayList<ColorPair> pairs = new ArrayList<>();
            currentGraph.setNodeColor(rowNum,colNum,col);
            if(checkContraints(currentGraph,rowNum,colNum,col)){
                pairs = addUsedColorPair(currentGraph,rowNum,colNum,col);
                if(!(rowNum == N-1 && colNum==N-1)){// gdy to nie koniec grafu
                    simpleGoThroughGraphBacktrackingAll(currentGraph,tmpRowNum,tmpColNum);
                }else{
                    btNum++;
                }
            }
            removeColorsPairs(pairs,currentGraph);
        }
    }



    public void simpleGoThroughGraphBacktrackingAllCenter(Graph g, int positionPointer){
        Graph currentGraph = new Graph(g,g.getDomains());
        int colNum =positions.get(positionPointer).colNum;
        int rowNum=positions.get(positionPointer).rowNum;
        for(Integer col: colors){
            ArrayList<ColorPair> pairs = new ArrayList<>();
            currentGraph.setNodeColor(rowNum,colNum,col);
            if(checkContraintsAround(currentGraph,rowNum,colNum,col)){
                pairs = addUsedColorPairAround(currentGraph,rowNum,colNum,col);
                if(positionPointer+1<positions.size()){// gdy to nie koniec grafu
                    simpleGoThroughGraphBacktrackingAllCenter(currentGraph,positionPointer+1);
                }else{
                    btNum++;
                }
                removeColorsPairs(pairs,currentGraph);
            }

        }
    }


    private void removeColorsPairs(ArrayList<ColorPair> pairs, Graph g){
        if(pairs.size()!=0){
            for(ColorPair cp: pairs){
                g.removeColors(cp.getColorFirst(),cp.getColorSecond());
            }
        }
    }


    private boolean checkContraints(Graph g,int rowNum, int colNum, int value){
        if(colNum!=0) {
            if (g.getNodeColor(rowNum, colNum - 1) == value ||
                    g.isPairOfColorUsed(g.getNodeColor(rowNum, colNum - 1), value)) {
                return false;
            }
        }
        if(rowNum!=0){
            if(g.getNodeColor(rowNum-1,colNum)==value ||
                    g.isPairOfColorUsed(g.getNodeColor(rowNum-1,colNum),value)){
                return false;
            }
        }
        return true;
    }


    private boolean checkContraintsAround(Graph g,int rowNum, int colNum, int value){
        if(colNum!=0) {
            if (g.getNodeColor(rowNum, colNum - 1) == value ||
                    g.isPairOfColorUsed(g.getNodeColor(rowNum, colNum - 1), value)) {
                return false;
            }
        }
        if(rowNum!=0){
            if(g.getNodeColor(rowNum-1,colNum)==value ||
                    g.isPairOfColorUsed(g.getNodeColor(rowNum-1,colNum),value)){
                return false;
            }
        }
        if(rowNum+1<N){
            if(g.getNodeColor(rowNum+1,colNum)==value ||
                    g.isPairOfColorUsed(g.getNodeColor(rowNum+1,colNum),value)){
                return false;
            }
        }
        if(colNum+1<N) {
            if (g.getNodeColor(rowNum, colNum + 1) == value ||
                    g.isPairOfColorUsed(g.getNodeColor(rowNum, colNum + 1), value)) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<ColorPair> addUsedColorPairAround(Graph g, int rowNum, int colNum, int color){
        ArrayList<ColorPair> pairs=  new ArrayList<>();
        if(colNum!=0 && g.getNodeColor(rowNum,colNum - 1)!=-1) {
            pairs.add(g.addColorPair(g.getNodeColor(rowNum,colNum - 1),color));
        }
        if(rowNum!=0 && g.getNodeColor(rowNum-1,colNum)!= -1){
            pairs.add(g.addColorPair(g.getNodeColor(rowNum-1,colNum),color));
        }
        if(rowNum+1<N && g.getNodeColor(rowNum+1,colNum)!= -1){
            pairs.add(g.addColorPair(g.getNodeColor(rowNum+1,colNum),color));
        }
        if(colNum+1<N && g.getNodeColor(rowNum,colNum + 1)!=-1) {
            pairs.add(g.addColorPair(g.getNodeColor(rowNum,colNum + 1),color));
        }
        return  pairs;
    }


    private ArrayList<ColorPair> addUsedColorPair(Graph g, int rowNum, int colNum, int color){
        ArrayList<ColorPair> pairs=  new ArrayList<>();
        if(colNum!=0) {
            pairs.add(g.addColorPair(g.getNodeColor(rowNum,colNum - 1),color));
        }
        if(rowNum!=0){
            pairs.add(g.addColorPair(g.getNodeColor(rowNum-1,colNum),color));
        }
        return  pairs;
    }

    public void forwardChecking(){
        Graph g = new Graph(N);
       // simpleForward(g,0,0,0);
    }

/*
    private boolean simpleForward(Graph g, int rowNum, int colNum, int domColIndex){
        Graph currentGraph = new Graph(g, g.getDomains());
        ArrayList<Vector> deletedDomains = new ArrayList<>();
        int color = currentGraph.getColorFromDomain(rowNum,colNum,domColIndex);
        currentGraph.setNodeColor(rowNum,colNum,color);

        addUsedColorPair(currentGraph, rowNum,colNum,color);
        updateAllDomains(currentGraph,deletedDomains);
        if(colNum==currentGraph.getGraph().length-1){
            if(rowNum==currentGraph.getGraph().length-1){
                graphF = new Graph(currentGraph);
                return true;
            }else{
                    rowNum++; colNum=0;
            }
        }else{
            colNum++;
        }
        return simpleForward(currentGraph, rowNum,colNum,0);
    }
*/
    public void simpleForwardAll(Graph g, int rowNum, int colNum){
        Graph currentGraph = new Graph(g, g.getDomains());
        ArrayList<Vector> deletedDomains = new ArrayList<>();
        ArrayList<ColorPair> pairs;
        int tmpRowNum = rowNum;
        int tmpColNum = colNum;
        if(colNum==N-1){
            tmpColNum=-1;
            tmpRowNum = rowNum+1;
        }
        tmpColNum++;
        if(! currentGraph.getDomains().isEmpty()){
            for(Integer color: currentGraph.getDomains().get(rowNum+"-"+colNum)){
                currentGraph.setNodeColor(rowNum,colNum,color);
                pairs = addUsedColorPair(currentGraph, rowNum,colNum,color);
                updateAllDomains(currentGraph,deletedDomains);
                if(!(rowNum == N-1 && colNum==N-1)){// gdy to nie koniec grafu
                    simpleForwardAll(currentGraph,tmpRowNum,tmpColNum);
                }else{
                    fcNum++;
                }
                removeColorsPairs(pairs,currentGraph);
                restoreDomains(currentGraph,deletedDomains);
                deletedDomains.clear();
            }

        }

    }


    public void simpleForwardAllCenter(Graph g,int positionPointer){
        Graph currentGraph = new Graph(g, g.getDomains());
        ArrayList<Vector> deletedDomains = new ArrayList<>();
        ArrayList<ColorPair> pairs;
        int rowNum = positions.get(positionPointer).rowNum;
        int colNum = positions.get(positionPointer).colNum;
        if(! currentGraph.getDomains().isEmpty()){
            for(Integer color: currentGraph.getDomains().get(rowNum+"-"+colNum)){
                currentGraph.setNodeColor(rowNum,colNum,color);
                pairs = addUsedColorPairAround(currentGraph, rowNum,colNum,color);
                updateAllDomains(currentGraph,deletedDomains);
                if(positionPointer+1<positions.size()){// gdy to nie koniec grafu
                    simpleForwardAllCenter(currentGraph,positionPointer+1);
                }else{
                    fcNum++;
                }
                removeColorsPairs(pairs,currentGraph);
                restoreDomains(currentGraph,deletedDomains);
                deletedDomains.clear();
            }

        }

    }

    public void updateAllDomains(Graph g, ArrayList<Vector> deletedDomains){

        //TODO : Uproscic sprawdzanie wg sasiadow nie wg wszystkich kolorow

        HashMap<String,ArrayList<Integer>> newDomains = new HashMap<>();
        initAllDomains(g,newDomains);
        boolean deleted = false;
        for( int i=0; i< g.getGraph().length; i++){
            for(int j=0;j<g.getGraph().length; j++){
                if(g.getGraph()[i][j]==-1){ // jezeli wezel nie ma jeszcze koloru
                    for(Integer currentColor: g.getNodeDomain(i,j)){ // sprawdzamy po kolei kazdy kolor
                        deleted = false;
                        if(i!=0 && g.getGraph()[i-1][j]!=-1){// gdy wezel powyzel nie ma koloru i nie null
                            if(g.isPairOfColorUsed(currentColor,g.getGraph()[i-1][j]) || currentColor==g.getGraph()[i-1][j]){
                                deleted = true;
                            }
                        }
                        if(!deleted && j+1<g.getGraph().length ){
                            if(g.isPairOfColorUsed(currentColor,g.getGraph()[i][j+1]) ||  currentColor==g.getGraph()[i][j+1]){
                                deleted=true;
                            }
                        }
                        if(!deleted && i+1<g.getGraph().length){
                            if(g.isPairOfColorUsed(currentColor,g.getGraph()[i+1][j]) || currentColor == g.getGraph()[i+1][j]){
                                deleted=true;
                            }
                        }
                        if(!deleted && j!=0&& g.getGraph()[i][j-1]!=-1 ){
                            if(g.isPairOfColorUsed(currentColor,g.getGraph()[i][j-1]) || currentColor==g.getGraph()[i][j-1]){
                                deleted = true;
                            }
                        }
                        if(deleted){
                            Vector v = new Vector(3);
                            v.add(i);
                            v.add(j);
                            v.add(currentColor);
                            deletedDomains.add(v);
                        }else{
                                ArrayList<Integer> l = newDomains.get(i+"-"+j);
                                l.add(currentColor);
                                newDomains.replace(i+"-"+j,l);
                        }
                    }
                }

            }
        }
        g.setDomainList(newDomains);
        return ;
    }

    private void initAllDomains(Graph g,HashMap<String,ArrayList<Integer>> domains ){
        for (int i =0; i<g.getGraph().length; i++){
            for (int j=0; j<g.getGraph().length; j++){
                domains.put(i+"-"+j,new ArrayList<Integer>());
            }
        }
    }

    private void restoreDomains(Graph g, ArrayList<Vector> deletedDomains){
        for(Vector v: deletedDomains){
            if(g.getDomains().containsKey(v.get(0)+"-"+v.get(1))){//domena istnieje
                ArrayList<Integer> domain = g.getDomains().get(v.get(0)+"-"+v.get(1)); // poprawic na nowa w razie probl.
                domain.add((Integer)v.get(2));
                g.getDomains().replace(v.get(0)+"-"+v.get(1),domain);
            }
        }

    }

}
