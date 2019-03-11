import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class Graph {

//  Read from the input file and create a graph object.

  private List<LinkedList<Node>> graph = new ArrayList<>();
  private int edges;
  private int vertices;

  Graph(String file) {
    // Read file
    graphFromFile(file);
  }

  LinkedList<Node> getAllEdges(int i) {
    return graph.get(i-1);
  }

  int getVertices() {
    return vertices;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Vertices  (vertex, edge weight)\n");
    sb.append("-------------------------------\n");
    for (int i = 0; i < graph.size(); i++) {
      sb.append(i+1);
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

  // TODO: make use of the line length number instead of reading until the
  //  end fo file
  private void graphFromFile(String file) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] values =  line.split(" ");
        // First line states graph properties
        if (values.length == 2) {
          // Create new graph with length stated on first line of input
          graph = new ArrayList<>();
          // Set number of edges and vertices from first line
          vertices = Integer.parseInt(values[0]);
          edges = Integer.parseInt(values[1]);
          // Instantiate linked lists for each vertex
          for (int i = 0; i < Integer.parseInt(values[0]); i++) {
            graph.add(new LinkedList<>());
          }
        } else if (values.length == 3) {
           // Add the vertex and edge to both places in the adjacency matrix
           graph.get(Integer.parseInt(values[0])-1)
               .add(new Node(
                   Integer.parseInt(values[1]),
                   Integer.parseInt(values[2])));

           graph.get(Integer.parseInt(values[1])-1)
               .add(new Node(
                   Integer.parseInt(values[0]),
                   Integer.parseInt(values[2])));
        } else {
          System.err.println("Invalid input file format...");
          throw new IOException();
        }
      }
    } catch (IOException e) {
      // e.printStackTrace();
      System.err.println("Error reading graph file.");
      System.exit(-1);
    }
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

