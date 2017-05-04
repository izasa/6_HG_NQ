import java.util.ArrayList;

/**
 * Created by izasa on 2017-04-07.
 */
public class Main {

    public static void main(String [] args){


        //NQ nq = new NQ(9);
        //nq.runsSimpleBacktracjking();
        //nq.runFrowardcheckingB();
        //System.out.println(nq.getSolutions());

        Graph g = new Graph(4);
        CSP csp = new CSP(g,4);
        csp.simpleGoThroughGraphBacktrackingAllCenter(g,0);
        //csp.simpleGoThroughGraphBacktrackingAll(g,0,0);
        System.out.println("romiar positins: "+csp.positions.size());
        System.out.println("rozw bt: "+csp.btNum );


        //Graph g = new Graph(3);
        //CSP csp = new CSP(g,3);
        //csp.simpleGoThroughGraphBacktrackingAll(g,0,0);
        //System.out.println("rozw bt: "+csp.btNum );

        //csp.simpleForwardAll(g,0,0);
        //System.out.println("rozw cf: "+csp.fcNum );
        /*



        csp.forwardChecking();

        csp.simpleGoThroughGraphBacktracking(0,0,0);
        for(int i =0; i<3; i++){
            for(int j=0; j<3;j++){
                System.out.print(g.getGraph()[i][j].toString()+" ");
            }
            System.out.println();
        }

        System.out.println("Forward");
        Graph forw = csp.graphF;
        for(int i =0; i<3; i++){
            for(int j=0; j<3;j++){
                System.out.print(forw.getGraph()[i][j].toString()+" ");
            }
            System.out.println();
        }

*/
    }

}
