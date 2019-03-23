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

  public static void main(String[] args) {
    File inputDirectory = new File(args[0]);

    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy hh:mm a");
    String outputFileName = date.format(dtf);

    try {

      CSVWriter writer = new CSVWriter(outputFileName + ".csv");
      writer.writeHead("File Name", "Trial Number", "Time", "Algorithm");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (inputDirectory.isDirectory()) {
      File[] files = inputDirectory.listFiles();
      if (files != null) {
        for (File file : files) {
          System.out.println("Running on: " + file.getName());
          Graph graph = new Graph(file.getAbsolutePath());

          System.out.println("STL");
          PrimSTL.primsSTL(graph, 1);

          System.out.println("DS");
          PrimDS.primsDS(graph, 1);
        }
      }
    } else {
      System.err.println("Invalid Directory Specified...Exiting");
      System.exit(-1);
    }
  }
}