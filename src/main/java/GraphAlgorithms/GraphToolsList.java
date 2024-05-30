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
		Queue<DirectedNode> fifo = new LinkedList<>();
		HashSet<DirectedNode> atteint = new HashSet<>();
		fifo.add(g.getNodeOfList(new DirectedNode(0)));
		atteint.add(g.getNodeOfList(new DirectedNode(0)));
		while (!fifo.isEmpty()) {
			DirectedNode n = fifo.poll();
			result.add(n);
			for (DirectedNode sn : n.getListSuccs()) {
				if (!atteint.contains(sn)) {
					fifo.add(sn);
					atteint.add(sn);
				}
			}

		}
		return result;
	}

	private static List<DirectedNode> explorerSommet(DirectedNode node, Set<DirectedNode> a) {
		List<DirectedNode> result = new ArrayList<>();
		result.add(node);
		a.add(node);
		for (DirectedNode t : node.getListSuccs()) {
			if (!a.contains(t)) {
				result.addAll(explorerSommet(t, a));
			}
		}
		return result;
	}

	private static List<DirectedNode> explorerGraphe(AdjacencyListDirectedGraph g) {
		HashSet<DirectedNode> atteint = new HashSet<>();
		List<DirectedNode> result = new ArrayList<>();
		for (DirectedNode n : g.getNodes()) {
			if (!atteint.contains(n)) {
				result.addAll(explorerSommet(n, atteint));
			}
		}
		return result;
	}

	public static List<DirectedNode> DFS(AdjacencyListDirectedGraph g) {
		return explorerGraphe(g);
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		System.out.println(al);

		int[][] dataGraph = new int[][]{
				new int[]{0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
				new int[]{	0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
				new int[]{	0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
				new int[]{	0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
				new int[]{	1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
				new int[]{	0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
				new int[]{	0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
				new int[]{	0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
				new int[]{	0, 1, 0, 0, 0, 1, 0, 0, 0, 0},
				new int[]{	0, 0, 1, 0, 0, 0, 1, 0, 0, 0}
		};
		System.out.println("=== BFS ===");
		// Les sommets 4 et 8 n'apparaissent pas car ce sont des sources.
		// Comme on commence par le sommet 0 et que le graphe est orienté, ils ne peuvent pas être explorés
		System.out.println(GraphToolsList.BFS(new AdjacencyListDirectedGraph(dataGraph)));
		System.out.println("===  ===");


		System.out.println("=== DFS ===");
		System.out.println(GraphToolsList.DFS(new AdjacencyListDirectedGraph(dataGraph)));
		System.out.println("===  ===");
	}
}
