package benchmark;

import java.io.File;
import java.io.IOException;
import java.util.Random;

// This class instantiate the tests
public class Main {
    /**
     * Runs the tests
     * <p>
     * Arguments: test file directory string, # iterations, graph generation seed
     *
     * @param args
     */
    public static void main(String[] args) {

//        Generate new graph files first

        Runtime rt = Runtime.getRuntime();
        try {
//            Generate full graphs (5 and 10 vertices)
            rt.exec("../generation/genGraphFull " +
                    "../inputs/5_" + args[2] + "_FULL.txt 5 20 " + //Output Filename
                    args[2]); // Random Seed
            rt.exec("../generation/genGraphFull " +
                    "../inputs/10_" + args[2] + "_FULL.txt 10 20 " + //Output Filename
                    args[2]); // Random Seed

//            Generate regular graphs that may have unconnected vertices (5 and 10 vertices)
            Random rand = new Random();
            rand.setSeed(Long.parseLong(args[2]));

            // Obtain a number between [0 - 10].
            int random = rand.nextInt(11);

            rt.exec("../generation/genGraph " +
                    " ../inputs/5_" + args[2] + "_REG_" + random + //Output Filename
                    " 5 " +   // Vertices
                    random +  // Degree
                    " 20 " +  // Max Edge Weight
                    args[2]); // Random Seed
            rt.exec("../generation/genGraph  " +
                    "../inputs/10_" + args[2] + "_REG_" + random + // Output Filename
                    " 10 " +  // Vertices
                    random +  // Degree
                    " 20 " +  // Max Edge Weight
                    args[2]); // Random Seed
        } catch (IOException e) {
            e.printStackTrace();
        }

        new FineBenchmark(args[0], Integer.parseInt(args[1]));
        new AverageBenchmark(args[0], Integer.parseInt(args[1]));


       // Delete generated files when finished
        File inputDirectory = new File(args[0]);
        for (File file :
                inputDirectory.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

    }
}