package benchmark;

import prims.Graph;

import java.io.File;

// This class instantiate the tests
public class Main {

  public static void main(String[] args) {
    File testDirectory = new File("../inputs");
    System.out.println(testDirectory.isDirectory());

    Graph graph = new Graph("../inputs/penta.txt");

    System.out.println(graph.toString());
  }
}
