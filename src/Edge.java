/**
 * ********************************************************  *********************************************************
 */
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
/** ********************************************************* */

/** {@link Edge} represents a single edge in an undirected graph. */
public class Edge {
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
