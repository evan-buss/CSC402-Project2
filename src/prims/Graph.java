package prims;

/* Author: Evan Buss                                        */
/* Major: Computer Science                                  */
/* Creation Date: March 13, 2019                            */
/* Due Date: March 22, 2019                                 */
/* Course: CSC402 - Data Structures 2                       */
/* Professor: Dr. Spiegel                                   */
/* Assignment: Project #2                                   */
/* Filename: prim.Graph.java                                */
/* Purpose: *See class header*                              */
/* Language: Java (Version 8)                               */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * {@link Graph} is an object that represents an adjacency list representation
 * of a graph. It reads from a properly formatted text file:
 * <ul>
 * <li>Line 1: [vertex count] [edge count]</li>
 * <li>Line 2-*: [vertex from] [vertex to] [edge weight]</li>
 * </ul>
 * The program will read the first line and then read lines until it reaches
 * the given edge count.
 * <p>Under the hood, the prim.Graph object is an {@link ArrayList} of
 * {@link LinkedList} of {@link Edge}.
 */
public class Graph {
    // prim.Graph is an adjaceny list.
    // It is stored as an ArrayList of LinkedLists containing prim.Edge objects
    private List<LinkedList<Edge>> graph = new ArrayList<>();
    private int edges;
    private int vertices;
    private int batchVertex;

    /**
     * Create a new {@link Graph} object from the specified input file
     *
     * @param file Name of the file to be read. Accepts relative and absolute
     *             paths as well.
     */
    public Graph(String file) {
//        graphFromFile(file); // Create graph object from file
        newReader(file);
    }

    /**
     * Get all edges for a specific vertex
     *
     * @param i graph vertex
     * @return LinkedList of all the edges connected to the specified vertex.
     */
    LinkedList<Edge> getAllEdges(int i) {
        return graph.get(i - 1);
    }

    /**
     * Get the total number of edges contained in the {@link Graph}
     *
     * @return number of edges in the prim.Graph
     */
    int getEdges() {
        return edges;
    }

    /**
     * Get the total number of vertices contained in the {@link Graph}
     *
     * @return number of vertices in the prim.Graph
     */
    public int getVertices() {
        return vertices;
    }

    /**
     * Get the first vertex read from the input file.
     * <p>
     * Useful for batch mode if the user doesn't want to specify a vertex
     *
     * @return first vertex read from file
     */
    int getBatchVertex() {
        return batchVertex;
    }

    /**
     * Generate a {@link Graph} from the given file name.
     *
     * <p>The input file should have the following format (Values must be
     * separated by a single space character):
     *
     * <ul>
     * <li>Line 1: [vertex count] [edge count]
     * <li>Line 2-*: [vertex from] [vertex to] [edge weight]
     * </ul>
     *
     * <p>The function will read the first line and then read lines until it
     * reaches the given edge count.
     *
     * @param file Name of the file to be read. Accepts relative and absolute
     *             paths as well.
     */
    private void graphFromFile(String file) {
        // Used to detect when the first line is ready to be read
        boolean readyToReadBatch = false;
        String line; // Line read from file.
        int linesLeft = 1; // Read at least a single line

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while (linesLeft > 0) {
                line = reader.readLine();
                // FIXME
                // graph generator has a bug where it outputs 2 spaces instead of 1
                // on first line
                String[] values = line.replace("  ", " ").split(" ");
                // First line states graph properties
                if (values.length == 2) {
                    // Set number of edges and vertices from first line
                    vertices = Integer.parseInt(values[0]);
                    // Create new graph with size based on given number of vertices
                    graph = new ArrayList<>(vertices);
                    edges = Integer.parseInt(values[1]);
                    linesLeft += edges;

                    // Instantiate linked lists for each vertex
                    for (int i = 0; i < Integer.parseInt(values[0]); i++) {
                        graph.add(new LinkedList<>());
                    }

                    readyToReadBatch = true;
                } else if (values.length == 3) {
                    // The first edge read from the file will be used as the source
                    // vertex if the user does not specify which vertex they wish to use
                    // as command line argument
                    if (readyToReadBatch) {
                        batchVertex = Integer.parseInt(values[0]);
                        readyToReadBatch = false;
                    }
                    // Add the vertex and edge to both places in the adjacency matrix
                    // Because the graph is undirected it's edges connect both directions
                    graph.get(Integer.parseInt(values[0]) - 1)
                            .add(new Edge(
                                    Integer.parseInt(values[0]),
                                    Integer.parseInt(values[1]),
                                    Integer.parseInt(values[2])));

                    graph.get(Integer.parseInt(values[1]) - 1)
                            .add(new Edge(
                                    Integer.parseInt(values[1]),
                                    Integer.parseInt(values[0]),
                                    Integer.parseInt(values[2])));
                } else {
                    System.err.println("Invalid input file format...");
                    throw new IllegalArgumentException();
                }
                linesLeft--;
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found.");
            System.exit(-1);
        } catch (IllegalArgumentException e) {
            System.err.println("File is not formatted properly.");
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading graph file.");
            System.exit(-1);
        }
    }


    /**
     * Alternate implementation of graphFromFile that accepts single value first lines
     * @param file graph file's name
     */
    private void newReader(String file) {
        // Used to detect when the first line is ready to be read
        boolean readyToReadBatch = false;
        String line; // Line read from file.
        int edgeCounter = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                // FIXME
                // graph generator has a bug where it outputs 2 spaces instead of 1
                // on first line
                String[] values = line.replace("  ", " ").split(" ");
                // First line states graph properties


                if (values.length == 2 || values.length == 1) {
                    // Set number of edges and vertices from first line
                    vertices = Integer.parseInt(values[0]);
                    // Create new graph with size based on given number of vertices
                    graph = new ArrayList<>(vertices);

                    // Instantiate linked lists for each vertex
                    for (int i = 0; i < Integer.parseInt(values[0]); i++) {
                        graph.add(new LinkedList<>());
                    }
                    readyToReadBatch = true;
                } else if (values.length == 3) {
                    // The first edge read from the file will be used as the source
                    // vertex if the user does not specify which vertex they wish to use
                    // as command line argument
                    if (readyToReadBatch) {
                        batchVertex = Integer.parseInt(values[0]);
                        readyToReadBatch = false;
                    }
                    // Add the vertex and edge to both places in the adjacency matrix
                    // Because the graph is undirected it's edges connect both directions
                    graph.get(Integer.parseInt(values[0]) - 1)
                            .add(new Edge(
                                    Integer.parseInt(values[0]),
                                    Integer.parseInt(values[1]),
                                    Integer.parseInt(values[2])));

                    graph.get(Integer.parseInt(values[1]) - 1)
                            .add(new Edge(
                                    Integer.parseInt(values[1]),
                                    Integer.parseInt(values[0]),
                                    Integer.parseInt(values[2])));

                    edgeCounter++;
                } else {
                    System.err.println("Invalid input file format...");
                    throw new IllegalArgumentException();
                }
            }

            edges = edgeCounter;
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found.");
            System.exit(-1);
        } catch (IllegalArgumentException e) {
            System.err.println("File is not formatted properly.");
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading graph file.");
            System.exit(-1);
        }
    }

    /**
     * Outputs the {@link Graph} in a properly formatted String.
     *
     * @return prim.Graph contents as a string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices  (from vertex, to vertex, edge weight)\n");
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
}
