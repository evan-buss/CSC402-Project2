public class PrimSTL {

  public static void main(String[] args) {

    if (args.length == 1) {
      // Create a new graph object from the input file given as command line arg
      Graph graph = new Graph(args[0]);
      System.out.println(graph.toString());
    } else {
      System.out.println("Usage: [file name]");
    }



  }
}
