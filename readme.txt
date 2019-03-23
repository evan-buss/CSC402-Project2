Project Details
===================================

  Summary:
      Project 2 is an implementation of Prim's algorithm to find the Minimum
      Spanning Tree of a given graph. There are 2 versions of the program.
      PrimSTL is written using Java's built-in data structures such as
      PriorityQueue. PrimDS is written using a custom implementation of a
      Priority Queue backed by a binary heap. The goal is to analyze the speed
      of both implementations.

  Project Timing:
      To time the speed of both implementations of Prim's algorithm, the program
      makes use of Java's System.nanoTime() method. The timer starts when the
      data file has been read and the Graph object is ready. The timer ends
      when the algorithm has finished computing the Minimum Spanning Tree of the
      given graph. The time is measured in nanoseconds and then converted to
      microseconds. All output occurs after the timing has finished because I
      found that writing to standard output drastically affects the time.

  Project Build Tool:
      The project makes use of the "ant" build tool. To run the project, you
      will need this tool.


Compiling the Program
===================================
    Note: The program is compiled into the "build/" directory.
          There are sub-folders for each implementation.
              - Ex) "build/DS" and "build/STL"

    STL:
        ant compileSTL

    DS:
        ant compileDS

Running the Program
===================================
    Note: These commands will compile the program as well. You need only run
          these commands to test the program.

    STL:
        ant runSTL

    DS:
        ant runDS

    Ant Instructions:
        1)
            You will be prompted to enter the filename of the properly formatted
            graph file. This can also contain the path to get to the file as
            well.
               - Ex) "inputs/file.txt" or "file.txt"
        2)
            You will then be prompted to choose a starting vertex. You can write
            a single vertex, a list of vertices, or you can leave it blank.
               - Ex) "1,2,3,4" or "1,1,1,1,1" or "4" or ""
               - Running the same vertex many times is a good way to see the
                 variations in speed over time.
        3)
            The program will then run and output the results.


Other Commands
===================================

    ant clean
        Remove the generated "build/" directory and all files within.

Data Structure Choices
===================================

    Graph.java
        The Graph object is a data structure representation of an Adjaceny
        Matrix.It is basically an ArrayList of LinkedLists of Edge objects. To
        retrieve all connected edges of a specific vertex, you need only access
        its entry in the array.

        For example if you wanted to get the edges attached to vertex 1, you
        would access array[0] and get the linked list of all edges. The linked
        list is implemented using Java's LinkedList class.

        I chose to use a linked list because I am not sure how many edges each
        vertex has when created. Linked lists are non-contiguous and allow me
        to add edges as they are read from the file. I also never need to
        retrieve any individual element of the linked list, rather I just need
        all of them to be added to the PriorityQueue at the same time.

        [
          Edge -> Edge,
          Edge -> Edge -> Edge,
          Edge -> Edge -> Edge -> Edge
        ]

    PrimSTL.java
        Prim's algorithm implemented with Java STL data structures. I use the
        Java STL PriorityQueue to store Edges that are accessible from the
        current Minimum Spanning Tree. The priority queue is essential to the
        algorithm because it orders the Edges inside from lowest to highest
        cost. To decide which edge to add the spanning tree next, I simply
        poll() the PriorityQueue to get the best option. Once an option has been
        chosen, I check to make sure it is not already in the ArrayList of
        chosen vertices, if it isn't I add it. The algorithm runs until nothing
        remains in the PriorityQueue.

    PrimDS.java
        Prim's algorithm implemented with custom data structures. I wrote my own
        priority queue of Edge objects instead of using the STL PriorityQueue.
        Also, I used a primitive int[] array instead of an ArrayList.

    EdgePriorityQueue.java
        A priority queue of Edge objects. It has methods to add and remove
        objects. The priority queue is backed by a binary heap. The binary heap
        uses a simple Edge[] array object to store the Edges in the queue.
        EdgePriorityQueue is implemented to be a minimum priority queue and
        return Edges with the lowest weight.

Documentation Website (Javadoc)
===================================

    http://unixweb.kutztown.edu/~ebuss376/CSC402/Project2/package-summary.html

NOTES
===================================
  - Due to the ant build system the source vertex input method is different than
    specified.

  - The ant build tool does not allow you to directly enter command line
    arguments but rather you must use go through the build.xml file.
    I had to get the program's input from the command line because ant does not
    allow you to read from "System.in" (standard input).

  - Due to the nature of getting command line args from ant, the program will
    have to be slightly adapted if not using ant but rather running directly
    from the command line.

    If you choose not to enter a source vertex, the ant tool still inserts a
    space character as an argument. Therefore in the code it just checks if
    arg[1] is an empty space to determine if it should run bash mode. If I
    was running this from bash without the ant build tool the code to
    determine the desired mode mode would use the standard "args.length".

  - Side note, the GenGraphFull.cpp file seems to insert two spaces between the
    the vertex count and edge count on the first line of the generated file. I
    have adapted my Graph function to replace the double space with a single
    space to account for this. Otherwise, trying to split the line by spaces
    results in an array with an extra item.