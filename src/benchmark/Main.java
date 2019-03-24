package benchmark;

import prims.Graph;
import prims.PrimDS;
import prims.PrimSTL;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// This class instantiate the tests
public class Main {
  static CSVWriter writer;

  public static void main(String[] args) {
    File inputDirectory = new File(args[0]);

    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy hh-mm a");
    String outputFileName = date.format(dtf);

    try {
      writer = new CSVWriter(outputFileName + ".csv");
      writer.writeLine(
          "File Name", "Algorithm", "Trial Number", "Vertex", "Time"
      );
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (inputDirectory.isDirectory()) {
      File[] files = inputDirectory.listFiles();
      if (files != null) {
        for (File file : files) {
          System.out.println("Running on: " + file.getName());
          Graph graph = new Graph(file.getAbsolutePath());
          runSTLBenchmark(graph, 100, file.getName());
          runDSBenchmark(graph, 100, file.getName());
        }
      }
      writer.close();
    } else {
      System.err.println("Invalid Directory Specified...Exiting");
      System.exit(-1);
    }
  }

  /**
   * Run the given graph through the STL implementation of Prims
   *
   * @param graph      Graph file that the algorithms should be run on
   * @param iterations Number of times each algorithm should be run
   */
  static void runSTLBenchmark(Graph graph, int iterations,
                              String fileName) {
    int vertices = graph.getVertices();

    for (int i = 0; i < vertices; i++) {
      for (int j = 0; j < iterations; j++) {
        long result = PrimSTL.primsSTL(graph, i + 1, false);
        writer.writeLine(fileName,
            "STL",
            Integer.toString(j + 1),
            Integer.toString(i + 1),
            Long.toString(result)
        );
      }
      writer.flush();
    }
  }

  /**
   * Run the given graph through the data structures implementation of Prims
   *
   * @param graph      Graph file that the algorithms should be run on
   * @param iterations Number of times each algorithm should be run
   */
  static void runDSBenchmark(Graph graph, int iterations,
                             String fileName) {
    int vertices = graph.getVertices();

    for (int i = 0; i < vertices; i++) {
      for (int j = 0; j < iterations; j++) {
        long result = PrimDS.primsDS(graph, i + 1, false);
        writer.writeLine(fileName,
            "DS",
            Integer.toString(j + 1),
            Integer.toString(i + 1),
            Long.toString(result)
        );
      }
      writer.flush();
    }
  }
}