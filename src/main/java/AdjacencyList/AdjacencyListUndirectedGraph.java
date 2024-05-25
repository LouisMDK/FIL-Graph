package AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;


public class AdjacencyListUndirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	protected List<UndirectedNode> nodes;
    protected int order;
    protected int m;

    
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
    
	public AdjacencyListUndirectedGraph() {
		 this.nodes = new ArrayList<>();
		 this.order = 0;
	     this.m = 0;
	}
	
	public AdjacencyListUndirectedGraph(List<UndirectedNode> nodes) {
		this.nodes = nodes;
        this.order = nodes.size();
        this.m = 0;
        for (UndirectedNode i : nodes) {
            this.m += i.getNbNeigh();
        }
        this.m = this.m/2; // we count 2 times each edges (in both way), so we divide by 2
    }

    public AdjacencyListUndirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(this.makeNode(i));
        }
        for (UndirectedNode n : this.getNodes()) {
            for (int j = n.getLabel(); j < matrix[n.getLabel()].length; j++) {
            	UndirectedNode nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getNeighbours().put(nn,0);
                    nn.getNeighbours().put(n,0);
                    this.m++;
                }
            }
        }
    }

    public AdjacencyListUndirectedGraph(AdjacencyListUndirectedGraph g) {
        super();
        this.order = g.getNbNodes();
        this.m = g.getNbEdges();
        this.nodes = new ArrayList<>();
        for (UndirectedNode n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (UndirectedNode n : g.getNodes()) {
        	UndirectedNode nn = this.getNodes().get(n.getLabel());
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
            	UndirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getNeighbours().put(snn,0);
                snn.getNeighbours().put(nn,0);
            }
        }
    }
    

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    
    /**
     * Returns the list of nodes in the graph
     */
    public List<UndirectedNode> getNodes() {
        return nodes;
    }

    /**
     * Returns the number of nodes in the graph (referred to as the order of the graph)
     */
    public int getNbNodes() {
        return this.order;
    }
    
    /**
     * @return the number of edges in the graph
     */ 
    public int getNbEdges() {
        return this.m;
    }

    /**
     * @return true if there is an edge between x and y
     */
    public boolean isEdge(UndirectedNode x, UndirectedNode y) {
        return x.getNeighbours().containsKey(y);
    }

    /**
     * Removes edge (x,y) if there exists one
     */
    public void removeEdge(UndirectedNode x, UndirectedNode y) {
    	if(isEdge(x,y)){
    		x.getNeighbours().remove(y);
            y.getNeighbours().remove(x);
            this.m--;
    	}
    }

    /**
     * Adds edge (x,y), requires that nodes x and y already exist
     * In non-valued graph, every edge has a cost equal to 0
     */
    public void addEdge(UndirectedNode x, UndirectedNode y) {
    	if(!isEdge(x,y)
                && nodes.contains(x)
                && nodes.contains(y)){
    		x.addNeigh(y,0);
    		y.addNeigh(x,0);
            this.m++;
    	}
    }

    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends UndirectedNode
     */
    public UndirectedNode makeNode(int label) {
        return new UndirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public UndirectedNode getNodeOfList(UndirectedNode v) {
        return this.getNodes().get(v.getLabel());
    }
    
    /**
     * @return a matrix representation of the graph 
     */
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];

        for (UndirectedNode n : nodes) {
                for (UndirectedNode sn : n.getNeighbours().keySet()) {
                    matrix[n.getLabel()][sn.getLabel()] = 1;
            }
        }

        return matrix;
    }

    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (UndirectedNode n : nodes) {
            s.append("neighbours of ").append(n).append(" : ");
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        GraphTools.afficherMatrix(mat);
        AdjacencyListUndirectedGraph al = new AdjacencyListUndirectedGraph(mat);
        System.out.println(al);
        System.out.println("(2,5) is it in the graph ? " +  al.isEdge(al.getNodes().get(2), al.getNodes().get(5)));

        // fix m for creation with a list of undirected nodes
        AdjacencyListUndirectedGraph al2 = new AdjacencyListUndirectedGraph(al.getNodes());
        System.out.println("al2");
        System.out.println(al.m);
        System.out.println(al2.m);

        // A completer
        System.out.println("Add edge (2,5)");
        al.addEdge(al.getNodes().get(2), al.getNodes().get(5));
        System.out.println("(2,5) is it in the graph ? " +  al.isEdge(al.getNodes().get(2), al.getNodes().get(5)));
        System.out.println(al);

        System.out.println("adjacency matrix of the graph");
        GraphTools.afficherMatrix(al.toAdjacencyMatrix());
    }


}
