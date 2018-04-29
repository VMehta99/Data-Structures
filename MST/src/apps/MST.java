package apps;

import structures.*;
import java.util.ArrayList;

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
		ArrayList<PartialTree.Arc> ret = new ArrayList<>();
		
		while(ptlist.size() > 1) {
			PartialTree tree = ptlist.remove();
			MinHeap<PartialTree.Arc> heap = tree.getArcs();
			
			while(!heap.isEmpty()) {
				PartialTree.Arc arc = heap.deleteMin();
				
				if(!belongsToTree(tree, arc.v2)) {
					tree.merge(ptlist.removeTreeContaining(arc.v2));
					ptlist.append(tree);
					ret.add(arc);
					
					break;
				}
			}
		}
		
		return ret;
	}
	
	public static boolean belongsToTree(PartialTree tree, Vertex v) {		
		try {
			PartialTreeList list = new PartialTreeList();
			list.append(tree);
			list.removeTreeContaining(v);
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
}