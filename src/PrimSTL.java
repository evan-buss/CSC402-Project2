import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrimSTL {

  public static void main(String[] args) {

    if (args.length == 2) {
      // Create a new graph object from the input file given as command line arg
      Graph graph = new Graph(args[0]);
      System.out.println(graph.toString());
      prims(graph, Integer.parseInt(args[1]));
    } else {
      System.out.println("Usage: [file name]");
    }
  }

  public static void prims(Graph graph, int vertex) {
    int weight = 0;
    PriorityQueue<Edge> availableEdges =
        new PriorityQueue<>(graph.getVertices(), new EdgeComparator());
    List<Integer> vertices = new ArrayList<>();

    long startNano = System.nanoTime();

    vertices.add(vertex); // Add starting vertex to list of vertices
    // Enqueue all adjacent vertices
    for (Graph.Node node : graph.getAllEdges(vertex)) {
      // Add all connected nodes to the priority queue
      availableEdges.add(
          new Edge(vertex,
              node.getVertex(),
              node.getEdgeWeight()));
    }

    while (!availableEdges.isEmpty()) {
      // Choose the lowest cost edge removing it from the p queue
      Edge chosenEdge = availableEdges.poll();
      if (chosenEdge != null) {
        vertex = chosenEdge.getTo();
      }

      if (!vertices.contains(vertex)) {
        weight += chosenEdge != null ? chosenEdge.getEdgeWeight() : 0;
        vertices.add(vertex); // Add the selected vertex to the set
        // Enqueue all adjacent vertices
        for (Graph.Node node : graph.getAllEdges(vertex)) {
          availableEdges.add(
              new Edge(vertex,
                  node.getVertex(),
                  node.getEdgeWeight()));
        }
      }
    }
    long endNano = System.nanoTime();

    System.out.println("Minimum Spanning Tree: ");
    System.out.println(vertices.toString());
    System.out.println("Weight: " + weight);

    System.out.println("Milliseconds: " + (double)(endNano - startNano) / 1000000);
    System.out.println("Nanoseconds: " + (endNano - startNano));
  }
}

class Edge {
  private int from;
  private int to;
  private int edgeWeight;

  public Edge(int from, int to, int edgeWeight) {
    this.from = from;
    this.to = to;
    this.edgeWeight = edgeWeight;
  }

  public int getFrom() {
    return from;
  }

  public int getTo() {
    return to;
  }

  int getEdgeWeight() {
    return edgeWeight;
  }

  @Override
  public String toString() {
    return "(" + from + ", " + to + ", " + edgeWeight + ")";
  }
}

// Comparator used by the priority queue to compare nodes by edge weight
class EdgeComparator implements Comparator<Edge> {
  @Override
  public int compare(Edge node1, Edge node2) {
    return node1.getEdgeWeight() - node2.getEdgeWeight();
  }
}
