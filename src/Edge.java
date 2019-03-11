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
