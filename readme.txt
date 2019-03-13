Project Details
===================================

  Summary:
      Project 2 is an implementation of Prim's algorithm to find the Minimum Spanning
      Tree of a given graph. There are 2 versions of the program. PrimSTL is
      written using Java's built-in data structures such as PriorityQueue. PrimDS
      is written using a custom implementation of a Priority Queue backed by a
      binary heap. The goal is to analyze the speed of both implementations.

  Project Timing
      To time the speed of both implementations of Prim's algorith, the program
      makes use of Java's System.nanoTime() method. The timer starts when the
      data file has been read and the Graph object is ready. The timer ends
      whent he algorithm has finished computing the Minimum Spanning Tree of the
      given graph. The time is measured in nanoseconds and then converted to
      microseconds. All output occurs after the timing has finished because I found
      that writing to standard output drastically affects the time.

  Project Build Tool
      The project makes use of the "ant" build tool. To run the project, you
      will need this tool.


Compiling the Program
===================================
The program is compiled into the build/ directory. There are sub-folders for each
implementation.

    STL:
        ant compileSTL

    DS:
        ant compileDS


Running the Program
===================================
(Note: These commands will compile the program as well. You need only run these
       commands to test the program.)

    STL:
        ant runSTL

    DS:
        ant runDS


Data Structure Choices
===================================

    Graph.java
        The Graph object is a data structure reprentation of an Adjaceny Matrix.
        It is basically an ArrayList of LinkedLists of Edge objects. To retreive
        all connected edges of a specific vertex, you need only access its entry
        in the array. For example if you wanted to get the edges attatched to
        vertex 1, you would access array[0] and get the linked list of all edges.
        The linked list is implemented using Java's LinkedList class. I chose to
        use a linked list because I am not sure how many edges each vertex has
        when created. Linked lists are non-contiguous and allow me to add edges
        as they are read from the file.

        [
          Edge -> Edge,
          Edge -> Edge -> Edge,
          Edge -> Edge -> Edge -> Edge
        ]

    PrimSTL.java
        Prim's algorithm implemented with Java STL data structures. I use the
        Java STL PriorityQueue to store Edges that are accessable from the current
        Minimum Spanning Tree. The priority queue is essential to the algorithm
        because it orders the Edges inside from lowest to highest cost. To decide
        which edge to add the spanning tree next, I simple poll() the PriorityQueue
        to get the best option. Once an option has been chosen, I check to make
        sure it is not already in the ArrayList of chosen vertices, if it isn't
        I add it.

    PrimDS.java
        Prim's algorithm implemented with custom data structures. I wrote my own
        priority queue of Edge objects instead of using the STL PriorityQueue. Also,
        I used a primitive int[] array instead of an ArrayList.

    EdgePriorityQueue.java
        A priority queue of Edge objects. It has methods to add and remove objects.
        The priority queue is implemented with a binary heap. The binary heap uses
        a simple Edge[] array object to store the Edges in the queue. EdgePriorityQueue
        is implemented to be a minimum priority queue and return Edges with the lowest
        weight.



Documentation Website
===================================

    http://unixweb.kutztown.edu/~ebuss376/CSC402/Project2/package-summary.html

NOTES
===================================
