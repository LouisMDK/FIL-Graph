package AdjacencyList;

import java.util.ArrayList;
import java.util.Map;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class AdjacencyListUndirectedValuedGraph extends AdjacencyListUndirectedGraph{

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public AdjacencyListUndirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.order = matrixVal.length;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (UndirectedNode n : this.getNodes()) {
            for (int j = n.getLabel(); j < matrixVal[n.getLabel()].length; j++) {
            	UndirectedNode nn = this.getNodes().get(j);
                if (matrixVal[n.getLabel()][j] != 0) {
                    n.getNeighbours().put(nn,matrixVal[n.getLabel()][j]);
                    nn.getNeighbours().put(n,matrixVal[n.getLabel()][j]);
                    this.m++;
                }
            }
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------
    

    /**
     * Adds the edge (from,to) with cost if it is not already present in the graph
     */
    public void addEdge(UndirectedNode x, UndirectedNode y, int cost) {
        if(!isEdge(x,y)
                && nodes.contains(x)
                && nodes.contains(y)){
            x.addNeigh(y,cost);
            y.addNeigh(x,cost);
            this.m++;
        }
    }

    /**
     * @return a matrix representation of the graph 
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];

        for (UndirectedNode n : nodes) {
            for (Map.Entry<UndirectedNode, Integer> sn : n.getNeighbours().entrySet()) {
                matrix[n.getLabel()][sn.getKey().getLabel()] = sn.getValue();
            }
        }

        return matrix;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (UndirectedNode n : nodes) {
            s.append("neighbours of ").append(n).append(" : ");
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
                s.append("(").append(sn).append(",").append(n.getNeighbours().get(sn)).append(")  ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 15, false, true, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        GraphTools.afficherMatrix(matrix);
        GraphTools.afficherMatrix(matrixValued);
        AdjacencyListUndirectedValuedGraph al = new AdjacencyListUndirectedValuedGraph(matrixValued);
        System.out.println(al);


        // Tests add edges
        System.out.println("=== test edge ===\n");

        UndirectedNode nd1 = al.getNodeOfList(new UndirectedNode(1));
        UndirectedNode nd2 = al.getNodeOfList(new UndirectedNode(2));
        System.out.println("is edge 1 -- 2 ?");
        System.out.println(al.isEdge(nd1, nd2));
        System.out.println("Add edge 1 -- 2, weight 5");
        al.addEdge(nd1, nd2, 5);
        System.out.println("new matrix");

        GraphTools.afficherMatrix(al.toAdjacencyMatrix());

        System.out.println("=== ===");
    }
}
