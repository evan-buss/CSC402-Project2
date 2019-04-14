package benchmark;

// This class instantiate the tests
public class Main {
    public static void main(String[] args) {
        new FineBenchmark(args[0], Integer.parseInt(args[1]));
        new AverageBenchmark(args[0], Integer.parseInt(args[1]));
    }
}