import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class Graph {

  //  Read from the input file and create a graph object.
  // TODO: Not sure if I can use the STL Linked List in both programs
  // Graph is an adjaceny list.
  //  It is stored as an ArrayList of LinkedLists containing Nodes
  private List<LinkedList<Node>> graph = new ArrayList<>();
  private int edges;
  private int vertices;

  Graph(String file) {
    graphFromFile(file); // Create graph object from file
  }

  LinkedList<Node> getAllEdges(int i) {
    return graph.get(i - 1);
  }

  public int getEdges() {
    return edges;
  }

  int getVertices() {
    return vertices;
  }

  private void graphFromFile(String file) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      int linesLeft = 1;
      while (linesLeft > 0) {
        line = reader.readLine();
        String[] values = line.split(" ");
        // First line states graph properties
        if (values.length == 2) {
          // Create new graph with length stated on first line of input


          // Set number of edges and vertices from first line
          vertices = Integer.parseInt(values[0]);
          graph = new ArrayList<>(vertices);
          edges = Integer.parseInt(values[1]);
          linesLeft += edges;

          // Instantiate linked lists for each vertex
          for (int i = 0; i < Integer.parseInt(values[0]); i++) {
            graph.add(new LinkedList<>());
          }
        } else if (values.length == 3) {
          // Add the vertex and edge to both places in the adjacency matrix
          graph
              .get(Integer.parseInt(values[0]) - 1)
              .add(new Node(Integer.parseInt(values[1]), Integer.parseInt(values[2])));

          graph
              .get(Integer.parseInt(values[1]) - 1)
              .add(new Node(Integer.parseInt(values[0]), Integer.parseInt(values[2])));
        } else {
          System.err.println("Invalid input file format...");
          throw new IOException();
        }
        linesLeft--;
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Error reading graph file.");
      System.exit(-1);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Vertices  (vertex, edge weight)\n");
    sb.append("-------------------------------\n");
    for (int i = 0; i < graph.size(); i++) {
      sb.append(i + 1);
      sb.append(" --> ");
      Iterator it = graph.get(i).iterator();
      while (it.hasNext()) {
        sb.append(it.next());
        if (it.hasNext()) {
          sb.append(" --> ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public class Node {
    private int vertex;
    private int edgeWeight;

    Node(int vertex, int edgeWeight) {
      this.vertex = vertex;
      this.edgeWeight = edgeWeight;
    }

    int getVertex() {
      return vertex;
    }

    int getEdgeWeight() {
      return edgeWeight;
    }

    @Override
    public String toString() {
      return "(" + vertex + ", " + edgeWeight + ")";
    }
  }
}
