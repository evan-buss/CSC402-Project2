import java.util.Arrays;

public class PrimDS {
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
        if (intVertex > 0 && intVertex < graph.getEdges()) {
          primsDS(graph, intVertex);
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

  private static void primsDS(Graph graph, int vertex) {
    int weight = 0;
    EdgePriorityQueue availableEdges =
        new EdgePriorityQueue(1);
    int index = 0;
    int[] vertices = new int[graph.getVertices()];

    long startNano = System.nanoTime();

    vertices[index] = vertex; // Add starting vertex to list of vertices
    index++;

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
      Edge chosenEdge = availableEdges.removeLeast();
      if (chosenEdge != null) {
        vertex = chosenEdge.getTo();
      }

      // If the Minimum Spanning Tree doesn't already have the vertex, add it
      if (!contains(vertices, vertex)) {
        weight += chosenEdge != null ? chosenEdge.getEdgeWeight() : 0;
        vertices[index] = vertex; // Add the selected vertex to the set
        index++;
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
    System.out.println(Arrays.toString(vertices));
    System.out.println("Weight: " + weight);
    System.out.println("Milliseconds: " +
        (double) (endNano - startNano) / 1000000 +
        " (" + (endNano - startNano) + ")\n");
  }

  private static boolean contains(int[] array, int value) {
    for (int v : array) {
      if (v == value) {
        return true;
      }
    }
    return false;
  }
}
