package apps;

import structures.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		PartialTreeList partials = new PartialTreeList();
		
		for(Vertex v : graph.vertices) {
			PartialTree pt = new PartialTree(v);
			Vertex.Neighbor n = v.neighbors;
			
			while(n != null) {
				pt.getArcs().insert(new PartialTree.Arc(v, n.vertex, n.weight));
				n = n.next;
			}
			
			partials.append(pt);
		}
		
		return partials;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		ArrayList<PartialTree.Arc> ret = new ArrayList<PartialTree.Arc>();
		Iterator<PartialTree> iter = ptlist.iterator();
		
		while(iter.hasNext()) {	
			PartialTree ptx = iter.next();
			
			if(inHeap(ptx.getArcs(), ptx.getArcs().getMin().v2)) {
				
			}
		}
		
		return null;
	}
	
	private static boolean inHeap(MinHeap<PartialTree.Arc> heap, Vertex v) {
		Iterator<PartialTree.Arc> iter = heap.iterator();
		
		while(iter.hasNext()) 
			if(iter.next().v1.name.equals(v.name))
				return true;
		
		return false;
	}
}