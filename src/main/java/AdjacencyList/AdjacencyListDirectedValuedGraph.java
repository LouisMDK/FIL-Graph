package AdjacencyList;

import java.util.ArrayList;
import java.util.Map;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

public class AdjacencyListDirectedValuedGraph extends AdjacencyListDirectedGraph {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public AdjacencyListDirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.order = matrixVal.length;
        this.nodes = new ArrayList<DirectedNode>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (DirectedNode n : this.getNodes()) {
            for (int j = 0; j < matrixVal[n.getLabel()].length; j++) {
            	DirectedNode nn = this.getNodes().get(j);
                if (matrixVal[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn,matrixVal[n.getLabel()][j]);
                    nn.getPreds().put(n,matrixVal[n.getLabel()][j]);
                    this.m++;
                }
            }
        }            	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    

    /**
     * Adds the arc (from,to) with cost  if it is not already present in the graph
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
        if (!isArc(from, to) && this.getNodes().contains(from) && this.getNodes().contains(to)) {
            from.addSucc(to, cost);
            to.addPred(from, cost);
            m++;
        }
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];
        for (int i = 0; i < order; i++) {
            for (Map.Entry<DirectedNode, Integer> j : nodes.get(i).getSuccs().entrySet()) {
                int IndSucc = j.getKey().getLabel();
                matrix[i][IndSucc] = j.getValue();
            }
        }
        return matrix;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(DirectedNode n : nodes){
            s.append("successors of ").append(n).append(" : ");
            for(DirectedNode sn : n.getSuccs().keySet()){
            	s.append("(").append(sn).append(",").append(n.getSuccs().get(sn)).append(")  ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.afficherMatrix(matrix);
        GraphTools.afficherMatrix(matrixValued);
        AdjacencyListDirectedValuedGraph al = new AdjacencyListDirectedValuedGraph(matrixValued);
        System.out.println(al);


        // Tests add arcs
        System.out.println("=== test arc ===\n");

        DirectedNode nd1 = al.getNodeOfList(new DirectedNode(1));
        DirectedNode nd2 = al.getNodeOfList(new DirectedNode(2));
        System.out.println("is arc 1 -> 2 ?");
        System.out.println(al.isArc(nd1, nd2));
        System.out.println("Add arc 1 -> 2, weight 5");
        al.addArc(nd1, nd2, 5);
        System.out.println("new matrix");

        GraphTools.afficherMatrix(al.toAdjacencyMatrix());

        System.out.println("=== ===");
    }
	
}
