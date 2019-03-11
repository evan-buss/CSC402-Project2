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

      // Make an array of all the given starting vertices
      String[] sources = args[1].split(",");
      for (String vertex : sources) {
        System.out.println("Source Vertex: " + vertex);
        int intVertex = Integer.parseInt(vertex);
        if (intVertex > 0 && intVertex < graph.getVertices()) {
          primsSTL(graph, intVertex);
        } else {
          System.out.println("Invalid source vertex... Exiting.");
          System.exit(-1);
        }
      }

    } else {
      System.out.println("Usage: [file name] " +
          "[comma separated source vertex list]");
    }
  }

  private static void primsSTL(Graph graph, int vertex) {
    int weight = 0;
    PriorityQueue<Edge> availableEdges =
        new PriorityQueue<>(graph.getEdges(), new EdgeComparator());
    List<Integer> vertices = new ArrayList<>();

    long startNano = System.nanoTime();

    vertices.add(vertex); // Add starting vertex to list of vertices

    // Enqueue all adjacent vertices
    for (Graph.Node node : graph.getAllEdges(vertex)) {
      availableEdges.add(
          new Edge(vertex,
              node.getVertex(),
              node.getEdgeWeight()));
    }

    // Run until there are no other possible vertices.
    while (!availableEdges.isEmpty()) {
      // Choose the lowest cost edge removing it from the p queue
      Edge chosenEdge = availableEdges.poll();
      if (chosenEdge != null) {
        vertex = chosenEdge.getTo();
      }

      // If the Minimum Spanning Tree doesn't already have the vertex, add it
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
    System.out.println("Milliseconds: " +
        (double) (endNano - startNano) / 1000000 +
        " (" + (endNano - startNano) + ")\n");
  }
}

// Comparator used by the priority queue to compare nodes by edge weight
class EdgeComparator implements Comparator<Edge> {
  @Override
  public int compare(Edge node1, Edge node2) {
    return node1.getEdgeWeight() - node2.getEdgeWeight();
  }
}
