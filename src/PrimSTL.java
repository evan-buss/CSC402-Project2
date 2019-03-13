/************************************************************/
/* Author: Evan Buss                                        */
/* Major: Computer Science                                  */
/* Creation Date: March 13, 2019                            */
/* Due Date: March 22, 2019                                 */
/* Course: CSC402 - Data Structures 2                       */
/* Professor: Dr. Spiegel                                   */
/* Assignment: Project #2                                   */
/* Filename: PrimSTL.java                                   */
/* Purpose:  *See class header*                             */
/* Language: Java (Version 8)                               */
/************************************************************/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Implementation of Prim's algorithm that finds the minimum
 * spanning tree of a {@link Graph}. This implementation makes use of Java's STL
 * {@link PriorityQueue} and {@link ArrayList} objects in the algorithm.
 *
 * <p>The algorithm is timed using the {@link System} nanoTime() method which
 * provides nano second accuracy. The algorithm's run time is displayed to
 * the console when finished.
 */

public class PrimSTL {

  /**
   * Run the Prim's algorithm using Java STL objects.
   *
   * <p>The program expects 2 arguments:
   *
   * <ul>
   * <li>Text file containing the graph that the algorithm should be run on
   * <li>List containing source vertex or vertices that the algorithm should start with. Such as:
   * <ul>
   * <li>1,2,3
   * <li>1
   * <li>1,1,1,1,2,2,2,3,3,4
   * </ul>
   * </ul>
   *
   * <p>The algorithm will loop until it runs once for each source vertex provided.
   *
   * @param args Program arguments of format described above.
   */
  public static void main(String[] args) {
    if (args.length == 2) {
      // Create a new graph object from the input file given as command line arg
      Graph graph = new Graph(args[0]);
      System.out.println(graph);

      // Make an array of all the given starting vertices
      String[] sources = args[1].split(",");
      // Run the algorithm until all source vertices have been run.
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
      System.out.println("Usage: [file name] " + "[comma separated source vertex list]");
    }
  }

  /**
   * Implementation of Prim's algorithm with STL data structures.
   *
   * <p>Namely an {@link PriorityQueue} and an {@link ArrayList}. Prints
   * the total weight of the Minimum Spanning Tree and the path it took when
   * finished. Also displays the total algorithm execution time in microseconds.
   *
   * @param graph  Graph object representing the graph loaded from a graph file
   * @param vertex The source vertex that the algorithm should start from
   */
  private static void primsSTL(Graph graph, int vertex) {
    int weight = 0;
    PriorityQueue<Edge> availableEdges = new PriorityQueue<>(graph.getEdges(), new EdgeComparator());
    List<Integer> vertices = new ArrayList<>();

    long startNano = System.nanoTime();

    vertices.add(vertex); // Add starting vertex to list of vertices

    // Enqueue all adjacent vertices
    for (Edge edge : graph.getAllEdges(vertex)) {
      availableEdges.add(new Edge(vertex, edge.getTo(), edge.getEdgeWeight()));
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
        for (Edge edge : graph.getAllEdges(vertex)) {
          availableEdges.add(new Edge(vertex, edge.getTo(),
              edge.getEdgeWeight()));
        }
      }
    }
    long endNano = System.nanoTime();
    System.out.println("Minimum Spanning Tree: ");
    System.out.println(vertices);
    System.out.println("Weight: " + weight);
    System.out.println("Milliseconds: " +
        (double) (endNano - startNano) / 1000 +
        " (" + (endNano - startNano) + ")\n");
  }
}

/**
 * Comparator used by the {@link PriorityQueue} to determine which edge has
 * lower weight.
 */
class EdgeComparator implements Comparator<Edge> {
  @Override
  public int compare(Edge edge1, Edge edge2) {
    return edge1.getEdgeWeight() - edge2.getEdgeWeight();
  }
}
