package apps;

import structures.Graph;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used for testing the MST
 */
public class Driver {
    public static void main(String[] args) {
        Graph graph = null;
        try {
            graph = new Graph("graph2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PartialTreeList partialTreeList = MST.initialize(graph);
        ArrayList<PartialTree.Arc> arcArrayList = MST.execute(partialTreeList);
        
        System.out.print("[");
        
        for (int i = 0; i < arcArrayList.size(); i++) {
            if(i == arcArrayList.size() - 1)
            	System.out.print(arcArrayList.get(i));
            else
            	System.out.print(arcArrayList.get(i) + ", ");
        }
        
        System.out.print("]");
    }
}
