/* Author: Evan Buss                                        */
/* Major: Computer Science                                  */
/* Creation Date: March 13, 2019                            */
/* Due Date: March 22, 2019                                 */
/* Course: CSC402 - Data Structures 2                       */
/* Professor: Dr. Spiegel                                   */
/* Assignment: Project #2                                   */
/* Filename: Edge.java                                      */
/* Purpose: *See class header*                              */
/* Language: Java (Version 8)                               */

/** {@link Edge} represents a single edge in an undirected graph. */
class Edge {
  private final int from;
  private final int to;
  private final int edgeWeight;

  /**
   * Creates a new {@link Edge} object.
   * @param from Vertex that the edge comes from (out)
   * @param to Vertex that the edges goes to (in)
   * @param edgeWeight Weight of the edge
   */
  Edge(int from, int to, int edgeWeight) {
    this.from = from;
    this.to = to;
    this.edgeWeight = edgeWeight;
  }

  /**
   * Get the vertex that the edge comes from (out)
   * @return vertex
   */
  public int getFrom() {
    return from;
  }

  /**
   * Get the vertex that the edges goes to (in)
   * @return vertex
   */
  public int getTo() {
    return to;
  }

  /**
   * Get the weight of the edge
   * @return edge weight
   */
  int getEdgeWeight() {
    return edgeWeight;
  }

  /**
   * Format the {@link Edge} to a String
   * @return String representation of the Edge
   */
  @Override
  public String toString() {
    return "(" + from + ", " + to + ", " + edgeWeight + ")";
  }
}
