import java.util.*;


/**
 * Created by izasa on 2017-03-31.
 */
public class CSP {

    private Graph graph;
    public Graph graphF;
    private HashMap<Integer,Integer> colorNumbers = new HashMap<>();// zbior <kolor,ilosc wystapien>
    private ArrayList<Integer> colors = new ArrayList<>();
    private int N;



    public CSP(Graph graph, int N) {
        this.graph = graph;
        this.N=N;
        initColors();

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


    public boolean simpleGoThroughGraphBacktracking(int rowNum, int colNum,int oldColor){
        boolean success = false;
        int value = oldColor;
        ArrayList<ColorPair> pairs = new ArrayList<>();
        while(value<colors.size() && !success){
            graph.setNodeColor(rowNum,colNum,value);
            if(checkContraints(rowNum,colNum,value)){
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

    private void removeColorsPairs(ArrayList<ColorPair> pairs, Graph g){
        if(pairs.size()!=0){
            for(ColorPair cp: pairs){
                System.out.println("usuwam pare kolorow: "+cp.getColorSecond()+cp.getColorSecond());
                g.removeColors(cp.getColorFirst(),cp.getColorSecond());
            }
        }
    }


    private boolean checkContraints(int rowNum, int colNum, int value){
        if(colNum!=0) {
            if (graph.getNodeColor(rowNum, colNum - 1) == value ||
                    graph.isPairOfColorUsed(graph.getNodeColor(rowNum, colNum - 1), value)) {
                return false;
            }
        }
        if(rowNum!=0){
            if(graph.getNodeColor(rowNum-1,colNum)==value ||
                    graph.isPairOfColorUsed(graph.getNodeColor(rowNum-1,colNum),value)){
                return false;
            }
        }
        return true;
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
        simpleForward(g,0,0,0);
    }


    private boolean simpleForward(Graph g, int rowNum, int colNum, int domColIndex){
        Graph currentGraph = new Graph(g, g.getDomains());
        ArrayList<Vector> deletedDomains = new ArrayList<>();
        System.out.println("ustawiam kolor dla "+rowNum+","+colNum);
        int color = currentGraph.getColorFromDomain(rowNum,colNum,domColIndex);
        currentGraph.setNodeColor(rowNum,colNum,currentGraph.getColorFromDomain(rowNum,colNum,domColIndex));

        addUsedColorPair(currentGraph, rowNum,colNum,color);
        //deletedColorsFromDomain= checkAllDomains(currentGraph,deletedDomains);
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

    public void updateAllDomains(Graph g, ArrayList<Vector> deletedDomains){
        HashMap<String,ArrayList<Integer>> newDomains = new HashMap<String,ArrayList<Integer>>();
        boolean deleted = false;
        for( int i=0; i< g.getGraph().length; i++){
            for(int j=0;j<g.getGraph().length; j++){
                if(g.getGraph()[i][j]==-1){ // jezeli wezel nie ma jeszcze koloru
                    System.out.println("Sprawdzam domene:"+i+","+j+": ");
                    for(Integer currentColor: g.getNodeDomain(i,j)){ // sprawdzamy po kolei kazdy kolor
                        System.out.println("Kolor: "+currentColor);
                        deleted = false;
                        if(i!=0 && g.getGraph()[i-1][j]!=-1){// gdy wezel powyzel nie ma koloru i nie null
                            System.out.println("if1");
                            if(g.isPairOfColorUsed(currentColor,g.getGraph()[i-1][j]) || currentColor==g.getGraph()[i-1][j]){
                                System.out.println("usuwam z domeny:"+i+","+j+": "+currentColor);
                                deleted = true;
                            }
                        }
                        if(!deleted && j+1<g.getGraph().length ){
                            System.out.println("if2");
                            if(g.isPairOfColorUsed(currentColor,g.getGraph()[i][j+1]) ||  currentColor==g.getGraph()[i][j+1]){
                                System.out.println("usuwam z domeny:"+i+","+j+": "+currentColor);
                                deleted=true;
                            }
                        }
                        if(!deleted && i+1<g.getGraph().length){
                            System.out.println("if3");
                            if(g.isPairOfColorUsed(currentColor,g.getGraph()[i+1][j]) || currentColor == g.getGraph()[i+1][j]){
                                System.out.println("usuwam z domeny:"+i+","+j+": "+currentColor);
                                deleted=true;
                            }
                        }
                        if(!deleted && j!=0&& g.getGraph()[i][j-1]!=-1 ){
                            System.out.println("if4");
                            if(g.isPairOfColorUsed(currentColor,g.getGraph()[i][j-1]) || currentColor==g.getGraph()[i][j-1]){
                                System.out.println("usuwam z domeny:"+i+","+j+": "+currentColor);
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
                            if(newDomains.containsKey(i+"-"+j)){
                                ArrayList<Integer> l = newDomains.get(i+"-"+j);
                                l.add(currentColor);
                                newDomains.replace(i+"-"+j,l);
                            }else{
                                ArrayList<Integer> l = new ArrayList<>();
                                l.add(currentColor);
                                newDomains.put(i+"-"+j,l);
                            }
                        }
                    }
                }

            }
        }
        g.setDomainList(newDomains);
        return ;
    }


    private void restoreDomain(Graph g, HashMap<String,ArrayList<Integer>> colors){
        for(String key: colors.keySet()){

        }
    }

    public void deleteColorFromDomains(Graph g, int rowNum, int colNum, int removedColor){
        HashMap<String,Integer> colors = new HashMap<>();
        if(rowNum!=0){
            g.removeColorFromDomain(rowNum-1,colNum,removedColor);
        }
        if(rowNum+1 < g.getGraph().length){
            g.removeColorFromDomain(rowNum+1, colNum, removedColor);
        }
        if(colNum!=0){
            g.removeColorFromDomain(rowNum,colNum-1,removedColor);
        }
        if(colNum+1 < g.getGraph().length){
            g.removeColorFromDomain(rowNum,colNum+1,removedColor);
        }
    }

}
