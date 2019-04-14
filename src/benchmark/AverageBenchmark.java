package benchmark;

import prims.Graph;
import prims.PrimDS;
import prims.PrimSTL;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AverageBenchmark {

    static CSVWriter writer;

    private static DecimalFormat df = new DecimalFormat(".##");

    public AverageBenchmark(String directory, int iterations) {
        File inputDirectory = new File(directory);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy hh_mm a");
        String outputFileName = date.format(dtf) + " - Averages (" + iterations + ")";

        try {
            writer = new CSVWriter(outputFileName + ".csv");
            writer.writeRow(
                    "File Name", "Vertex", "STL Average", "DS Average"
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
                    runBenchmarkSummary(graph, iterations, file.getName());
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
    private static void runBenchmarkSummary(Graph graph, int iterations,
                                            String fileName) {
        int vertices = graph.getVertices();

//        Hold results for each vertex for each iteration run
        long[][] stlResults = new long[vertices][iterations];
        long[][] dsResults = new long[vertices][iterations];

//        Hold results of averages per vertex
        double[] stlAverages = new double[vertices];
        double[] dsAverages = new double[vertices];


        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < iterations; j++) {
                stlResults[i][j] = PrimSTL.primsSTL(graph, i + 1, false);
                dsResults[i][j] = PrimDS.primsDS(graph, i + 1, false);
            }
        }

        //    Get the average time for each vertex
        for (int i = 0; i < vertices; i++) {
            long stlTotal = 0;
            long dsTotal = 0;
            for (int j = 0; j < iterations; j++) {
                stlTotal += stlResults[i][j];
                dsTotal += dsResults[i][j];
            }

            stlAverages[i] = (stlTotal / (double) iterations) / 1000;
            dsAverages[i] = (dsTotal / (double) iterations) / 1000;

            writer.writeRow(fileName,
                    Integer.toString(i + 1),
                    df.format(stlAverages[i]),
                    df.format(dsAverages[i])
            );
        }

//        Calculate graph average
        double stlAverage = 0;
        double dsAverage = 0;
        for (int i = 0; i < stlAverages.length; i++) {
            stlAverage += stlAverages[i];
            dsAverage += dsAverages[i];
        }

        writer.writeRow(fileName,
                "ALL",
                df.format(stlAverage / vertices),
                df.format(dsAverage / vertices));

        writer.flush();
    }
}
