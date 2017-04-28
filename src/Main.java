import java.util.ArrayList;

/**
 * Created by izasa on 2017-04-07.
 */
public class Main {

    public static void main(String [] args){
        Graph g = new Graph(4);
        CSP csp = new CSP(g,4);
        //csp.simpleGoThroughGraphBacktrackingAll(g,0,0);
        //System.out.println("rozw bt: "+csp.btNum );

        csp.simpleForwardAll(g,0,0);
        System.out.println("rozw cf: "+csp.fcNum );
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

        /*
        Graph gr = new Graph(g);
        System.out.println("skopiowany: ");
        for(int i =0; i<3; i++){
            for(int j=0; j<3;j++){
                System.out.print(gr.getGraph()[i][j].toString()+" ");
            }
            System.out.println();
        }
        gr.setNodeColor(0,0,5);
        gr.setNodeColor(0,1,5);
        gr.setNodeColor(0,2,5);
        System.out.println(" autentyk po zmianie kopii : ");
        for(int i =0; i<3; i++){
            for(int j=0; j<3;j++){
                System.out.print(g.getGraph()[i][j].toString()+" ");
            }
            System.out.println();
        }

        ArrayList list = new ArrayList();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        System.out.println("\nlista:");
        for(int i=0;i<list.size(); i++){
            System.out.print(list.get(i));
        }
        ArrayList l2 = new ArrayList(list);
        l2.add(2,6);
        System.out.println("\nduplikta:");
        for(int i=0;i<list.size(); i++){
            System.out.print(l2.get(i));
        }
        System.out.println("\nlista po zmianie w duplikacie:");
        for(int i=0;i<list.size(); i++){
            System.out.print(list.get(i));
        }
        */
    }

}
