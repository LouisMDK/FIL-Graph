package GraphAlgorithms;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import AdjacencyList.AdjacencyListDirectedGraph;
import AdjacencyList.AdjacencyListDirectedValuedGraph;
import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import Collection.Triple;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------

	public static List<DirectedNode> BFS(AdjacencyListDirectedGraph g) {
		List<DirectedNode> result = new ArrayList<>();
		Queue<DirectedNode> fifo = new LinkedList<DirectedNode>();
		boolean[] marqued = new boolean[g.getNbNodes()];
		fifo.add(g.getNodeOfList(new DirectedNode(0)));
		while (fifo.size() > 0){
			DirectedNode currentNode = fifo.poll();
			for (DirectedNode n : currentNode.getListSuccs()){
				if (!marqued[n.getLabel()]) {
					fifo.add(n);
					marqued[n.getLabel()] = true;
				}
			}
			result.add(currentNode);
		}
		return result;
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		System.out.println(al);

		System.out.println("=== BFS ===");
		int[][] dataGraph = new int[][]{
				new int[]{0, 1, 1, 0, 1, 0, 0},
				new int[]{0, 0, 0, 1, 0, 1, 0},
				new int[]{0, 0, 0, 0, 0, 0, 1},
				new int[]{0, 0, 0, 0, 0, 0, 0},
				new int[]{0, 0, 0, 0, 0, 1, 0},
				new int[]{0, 0, 0, 0, 0, 0, 0},
				new int[]{0, 0, 0, 0, 0, 0, 0},
		}; // exemple => @see https://fr.wikipedia.org/wiki/Algorithme_de_parcours_en_largeur#Exemple
		System.out.println(GraphToolsList.BFS(new AdjacencyListDirectedGraph(dataGraph)));
		System.out.println("===  ===");
	}
}
