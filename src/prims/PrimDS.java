package prims;

/* Author: Evan Buss                                        */
/* Major: Computer Science                                  */
/* Creation Date: March 13, 2019                            */
/* Due Date: March 22, 2019                                 */
/* Course: CSC402 - Data Structures 2                       */
/* Professor: Dr. Spiegel                                   */
/* Assignment: Project #2                                   */
/* Filename: prim.PrimDS.java                                    */
/* Purpose:  *See class header*                             */
/* Language: Java (Version 8)                               */

import java.util.Arrays;

/**
 * Implementation of Prim's algorithm that finds the minimum spanning tree of a
 * {@link Graph}. This implementation makes use of primitive data structures
 * without using any STL structures. The biggest change is the use of
 * {@link EdgePriorityQueue} instead of the STL {@link java.util.PriorityQueue}
 * class.
 *
 * <p>The algorithm is timed using the {@link System} nanoTime() method which
 * provides nano second accuracy. The algorithm's run time is displayed to
 * the console when finished.
 *
 * <p>Prim algorithm adapted from the "pseudocode" found at
 * <a href="https://visualgo.net/en/mst">visualgo.net</a>
 */
public class PrimDS {

  /**
   * Run the Prim's algorithm using custom data structures.
   *
   * <p>The program expects 2 arguments:
   *
   * <ul>
   * <li>Text file containing the graph that the algorithm should be run on
   * <li>List containing source vertex or vertices that the algorithm should
   * start with. Such
   * as:
   * <ul>
   * <li>1,2,3
   * <li>1
   * <li>1,1,1,1,2,2,2,3,3,4
   * <li>Leave blank to use the vertex read from first line of input file</li>
   * </ul>
   * </ul>
   * <p>The algorithm will loop until it runs once for each source vertex
   * provided.
   *
   * @param args Program arguments of format described above.
   */
  public static void main(String[] args) {
    if (args.length == 2) {
      // Create a new graph object from the input file given as command line arg
      Graph graph = new Graph(args[0]);
      System.out.println(graph);

      // Batch Mode
      // Second arg is empty space if nothing entered (due to ant build system)
      if (args[1].equals("")) {
        System.out.println("Running in Batch Mode...");
        System.out.println("Source Vertex: " + graph.getBatchVertex() + "\n");
        primsDS(graph, graph.getBatchVertex());
      } else {
        // Make array of starting vertices. Run algorithm for each one
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
      }
    } else {
      System.out.println("Usage: [file name] <source vertex/vertices>");
    }
  }

  /**
   * Implementation of Prim's algorithm with custom data structures.
   *
   * <p>Namely an {@link EdgePriorityQueue} and a standard int[] array. Prints
   * the total weight of the Minimum Spanning Tree and the path it took when
   * finished. Also displays the total algorithm execution time in microseconds.
   *
   * @param graph  prim.Graph object representing the graph loaded from a graph file
   * @param vertex The source vertex that the algorithm should start from
   */
  public static void primsDS(Graph graph, int vertex) {
    int weight = 0;
    EdgePriorityQueue availableEdges =
        new EdgePriorityQueue(graph.getVertices());
    int index = 0; // Next open index in the vertex list
    int[] vertices = new int[graph.getVertices()];

    long startNano = System.nanoTime();

    vertices[index] = vertex; // Add starting vertex to list of vertices
    index++;

    // Enqueue all adjacent vertices
    for (Edge edge : graph.getAllEdges(vertex)) {
      availableEdges.add(new Edge(vertex, edge.getTo(), edge.getEdgeWeight()));
    }

    // Run until there are no other possible paths left
    while (!availableEdges.isEmpty()) {
      // Choose the lowest cost edge removing it from the p queue
      Edge chosenEdge = availableEdges.removeLeast();
      vertex = chosenEdge.getTo(); // Get the vertex associated with the edge

      // If the Minimum Spanning Tree doesn't already have the vertex, add it
      if (!contains(vertices, vertex)) {
        weight += chosenEdge.getEdgeWeight(); // Increment weight
        vertices[index] = vertex; // Add the selected vertex to the set
        index++;

        // Enqueue all adjacent vertices
        for (Edge edge : graph.getAllEdges(vertex)) {
          availableEdges.add(new Edge(
              vertex,
              edge.getTo(),
              edge.getEdgeWeight()));
        }
      }
    }
    long endNano = System.nanoTime();

    System.out.println("Minimum Spanning Tree: ");
    System.out.println("\t" + Arrays.toString(vertices));
    System.out.println("\tTotal Cost: " + weight);
    // Convert nanoseconds to microseconds
    System.out.println("\tMicroseconds: " +
        (double) (endNano - startNano) / 1000 +
        " (" + (endNano - startNano) + ")\n");
  }

  /**
   * Helper method to check if an array already contains a certain integer.
   * <p>
   * Loops through the given array until it finds the specified value.
   *
   * @param array int[] array to search
   * @param value value to search for in the array
   * @return Returns true if value is found. false otherwise.
   */
  private static boolean contains(int[] array, int value) {
    for (int v : array) {
      if (v == value) {
        return true;
      }
    }
    return false;
  }
}
