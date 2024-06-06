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

	private static List<DirectedNode> explorerSommet(DirectedNode node) {
		List<DirectedNode> result = new ArrayList<>();

		visite[node.getLabel()] = 1; // en cours de visite
		// debut[node.getLabel()] = cpt++;

		result.add(node);
		for (DirectedNode t : node.getListSuccs()) {
			if (visite[t.getLabel()] == 0) {
				result.addAll(explorerSommet(t));
			}
		}

		visite[node.getLabel()] = 2; // totalement visité
		// fin[node.getLabel()] = cpt++;
		order_CC.add(node.getLabel());

		return result;
	}

	private static List<DirectedNode> explorerGraphe(AdjacencyListDirectedGraph g) {
		visite = new int[g.getNbNodes()]; // non visité
		/*
		debut = new int[g.getNbNodes()];
		fin = new int[g.getNbNodes()];
		cpt = 0;
		 */
		order_CC = new LinkedList<>();

		List<DirectedNode> result = new ArrayList<>();
		for (DirectedNode n : g.getNodes()) {
			if (visite[n.getLabel()] == 0) {
				result.addAll(explorerSommet(n));
			}
		}
		return result;
	}

	private static List<DirectedNode> explorerSommetBis(DirectedNode node) {
		List<DirectedNode> result = new ArrayList<>();
		visite[node.getLabel()] = 1; // en cours de visite

		result.add(node);
		for (DirectedNode t : node.getListSuccs()) {
			if (visite[t.getLabel()] == 0) {
				result.addAll(explorerSommetBis(t));
			}
		}
		visite[node.getLabel()] = 2; // totalement visité
		return result;
	}

	private static List<List<DirectedNode>> explorerGrapheBis(AdjacencyListDirectedGraph g, List<Integer> order_CC) {
		visite = new int[g.getNbNodes()]; // non visité

		List<List<DirectedNode>> result = new ArrayList<>();
		for (int i = order_CC.size()-1; i >= 0; i--) {
			if (visite[order_CC.get(i)] == 0) {
				System.out.println("Exploration du sommet : "+order_CC.get(i));
				result.add(explorerSommetBis(g.getNodeOfList(new DirectedNode(order_CC.get(i)))));
				System.out.println("Sommets visités : "+result.get(result.size()-1));
			}
		}
		return result;
	}

	public static List<DirectedNode> DFS(AdjacencyListDirectedGraph g) {
		return explorerGraphe(g);
	}

	private static boolean containsFalse(boolean[] tab) {
		for (boolean b : tab) {
			if (!b) {
				return true;
			}
		}
		return false;
	}
	public static void dijkstra(AdjacencyListDirectedValuedGraph g, DirectedNode source) {
		boolean[] mark = new boolean[g.getNbNodes()];
		int[] val = new int[g.getNbNodes()];
		DirectedNode[] pred = new DirectedNode[g.getNbNodes()];
		for (int i = 0; i < g.getNbNodes(); i++) {
			val[i] = Integer.MAX_VALUE / 2;
			pred[i] = null;
			mark[i] = false;
		}
		val[source.getLabel()] = 0;
		pred[source.getLabel()] = source;
		while(containsFalse(mark)) {
			int x = 0;
			int min = (Integer.MAX_VALUE / 2) + 1;
			for (int y = 0; y < g.getNbNodes(); y++) {
				if (!mark[y] && val[y] < min) {
					min = val[y];
					x = y;
				}
			}
				mark[x] = true;
				for (int y = 0; y < g.getNbNodes(); y++) {
					int cout;
					if (g.getNodeOfList(new DirectedNode(x)).getSuccs().get(g.getNodeOfList(new DirectedNode(y))) == null) {
						cout = Integer.MAX_VALUE / 2;
					} else {
						cout = g.getNodeOfList(new DirectedNode(x)).getSuccs().get(g.getNodeOfList(new DirectedNode(y)));
					}
					if (!mark[y] && val[x] + cout < val[y]) {
						val[y] = val[x] + cout;
						pred[y] = g.getNodeOfList(new DirectedNode(x));
					}
				}
			}
		System.out.println("Valeurs : "+Arrays.toString(val));
		System.out.println("Prédécesseurs : "+Arrays.toString(pred));
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

		System.out.println("=== Graph ===");
		GraphTools.afficherMatrix(dataGraph);

		System.out.println("=== BFS ===");
		// Les sommets 4 et 8 n'apparaissent pas car ce sont des sources.
		// Comme on commence par le sommet 0 et que le graphe est orienté, ils ne peuvent pas être explorés
		System.out.println(GraphToolsList.BFS(new AdjacencyListDirectedGraph(dataGraph)));
		System.out.println("===  ===");


		System.out.println("=== DFS ===");
		System.out.println(GraphToolsList.DFS(new AdjacencyListDirectedGraph(dataGraph)));
		System.out.println("===  ===");

		System.out.println("=== Composantes Fortement Connexes ===");
		AdjacencyListDirectedGraph g = new AdjacencyListDirectedGraph(dataGraph);
		System.out.println("Exploration : " + GraphToolsList.explorerGraphe(g));
		System.out.println("SCC : " + GraphToolsList.explorerGrapheBis(g.computeInverse(), order_CC));
		System.out.println("===  ===");

		System.out.println("=== Dijkstra ===");
		GraphToolsList.dijkstra(new AdjacencyListDirectedValuedGraph(dataGraph), new DirectedNode(0));
		System.out.println("===  ===");
	}
}
