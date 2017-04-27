import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by izasa on 2017-03-31.
 */
public class Graph {

    private Integer [][] graph;
    private ArrayList<String> colorsL = new ArrayList<>(); // list with used colors
    private HashMap<String,ArrayList<Integer>>  domains ;
    private ArrayList<Integer> colorsNum = new ArrayList<>();

    public Graph(int graphSize) {
        graph = new Integer[graphSize][graphSize];
        domains= new HashMap<>();
        initNodes();
        initColors();// byc moze niepotrzebne - na razie
        initDomains();// to samo
    }


    public Graph(Graph g,HashMap<String,ArrayList<Integer>>  dom){
        this.graph = new Integer[g.getGraph().length][g.getGraph().length];
        for(int i=0;i<g.getGraph().length; i++){
            for(int j=0;j<g.getGraph().length; j++){
                graph[i][j]= new Integer(g.getGraph()[i][j]);
            }
        }
        domains= new HashMap<>();
        copyUsedColors(g.colorsL);
        initColors();
        deepDomainCopy(dom);
    }

    public Graph(Graph g){
        this.graph = new Integer[g.getGraph().length][g.getGraph().length];
        for(int i=0;i<g.getGraph().length; i++){
            for(int j=0;j<g.getGraph().length; j++){
                graph[i][j]= new Integer(g.getGraph()[i][j]);
            }
        }
        domains= new HashMap<>();
        copyUsedColors(g.colorsL);
        initColors();
        initDomains();
    }

    public Integer[][] getGraph() {
        return graph;
    }

    private void initNodes(){
        for(int i=0;i<graph.length; i++){
            for(int j=0;j<graph.length; j++){
                graph[i][j]= -1;
            }
        }
    }

    private void initColors(){
        int colorsSetSize =  graph.length%2==0 ? 2*graph.length : (2*graph.length)+1;
        for(int i =0; i<colorsSetSize; i++){
            colorsNum.add(i);
        }
    }

    private void copyUsedColors(ArrayList<String> col){
        for(String s: col){
            colorsL.add(new String(s));
        }
    }

    public void deepDomainCopy(HashMap<String,ArrayList<Integer>> dom){
        for (String key: dom.keySet()){
            domains.put(new String(key),new ArrayList<Integer>(dom.get(key)));
        }
    }

    public HashMap<String, ArrayList<Integer>> getDomains() {
        return domains;
    }

    public void setGraph(Integer[][] graph) {
        this.graph = graph;
    }

    public void setNodeColor(int rowNum, int colNum, int color){
        graph[rowNum][colNum] = color;
    }

    public int getNodeColor(int rowNum, int colNum){
        int color;
        if( isSetColor(rowNum, colNum)){
            color = graph[rowNum][colNum];
        }else{
            color =-1;
        }
        return color;
    }

    public boolean isSetColor(int rowNum, int colNum){
        return graph[rowNum][colNum] != null;
    }


    public boolean isPairOfColorUsed(int colorFirst, int colorSecond){
        if(colorsL.contains(colorFirst+"-"+colorSecond) ||
                colorsL.contains(colorSecond+"-"+colorFirst)){
            return true;
        }
        return false;
    }


    public ColorPair addColorPair(int colorFirst, int colorSecond) {
        ColorPair pair = new ColorPair(colorFirst, colorSecond);
        colorsL.add(colorFirst+"-"+colorSecond);
        return pair;
    }

    /*
    public void removeColorPair(ColorPair pair){
        colorsL.remove(pair);
    }
    */

    public void removeColors(int colorFirst, int colorSecond){
        if(colorsL.contains(colorFirst+"-"+colorSecond)){
            colorsL.remove(colorFirst+"-"+colorSecond);
        }else{
            if(colorsL.contains(colorSecond+"-"+colorFirst)){
                colorsL.remove(colorSecond+"-"+colorFirst);
            }
        }
    }

    public void initDomains(){
        for (int i =0; i<graph.length; i++){
            for (int j=0; j<graph.length; j++){
                domains.put(i+"-"+j,new ArrayList<Integer>(colorsNum));
            }
        }
    }

    public void removeColorFromDomain(int rowNum, int colNum, int removedColor){
        ArrayList l = domains.get(rowNum+"-"+colNum);
        l.remove(removedColor);
        domains.replace(rowNum+"-"+colNum,l);
    }

    public int getColorFromDomain(int rowNum, int colNum, int index){
        return domains.get(rowNum+"-"+colNum).get(index);
    }

    public ArrayList<Integer> getNodeDomain(int rowNum, int colNum){
        return domains.get(rowNum+"-"+colNum);
    }

    public void setDomains(int rowNum, int colNum,ArrayList<Integer> newDomain){
        domains.replace(rowNum+"-"+colNum,newDomain);
    }

    public void setDomainList(HashMap<String,ArrayList<Integer>> newDomains){
        this.domains = newDomains;
    }
}
