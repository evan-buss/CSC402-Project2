package benchmark;

import prims.Graph;
import prims.PrimDS;
import prims.PrimSTL;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FineBenchmark {

    static CSVWriter writer;

    FineBenchmark(String directory, int iterations) {
        File inputDirectory = new File(directory);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy hh_mm a");
        String outputFileName = date.format(dtf) + " - DataOnly (" + iterations + ")";


        try {
            writer = new CSVWriter(outputFileName + ".csv");
            writer.writeRow(
                    "File Name", "Algorithm", "Trial Number", "Vertex", "Time"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputDirectory.isDirectory()) {
            File[] files = inputDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    Graph graph = new Graph(file.getAbsolutePath());
                    runSTLBenchmark(graph, iterations, file.getName());
                    runDSBenchmark(graph, iterations, file.getName());
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
    private static void runSTLBenchmark(Graph graph, int iterations,
                                        String fileName) {
        int vertices = graph.getVertices();

        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < iterations; j++) {
                long result = PrimSTL.primsSTL(graph, i + 1, false);
                writer.writeRow(fileName,
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
    private static void runDSBenchmark(Graph graph, int iterations,
                                       String fileName) {
        int vertices = graph.getVertices();

        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < iterations; j++) {
                long result = PrimDS.primsDS(graph, i + 1, false);
                writer.writeRow(fileName,
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
