package prims;

/* Author: Evan Buss                                        */
/* Major: Computer Science                                  */
/* Creation Date: March 13, 2019                            */
/* Due Date: March 22, 2019                                 */
/* Course: CSC402 - Data Structures 2                       */
/* Professor: Dr. Spiegel                                   */
/* Assignment: Project #2                                   */
/* Filename: prim.EdgePriorityQueue.java                         */
/* Purpose: *See class header*                              */
/* Language: Java (Version 8)                               */

import java.util.Arrays;

/**
 * Implementation of a priority queue of {@link Edge} objects. Makes use of an
 * underlying binary heap to contain queue values and return the least cost edge
 *
 * <p>Binary heap implementation based on Hacker Rank's video on Heaps
 * <a href="https://www.youtube.com/watch?v=t0Cq6tVNRBA">found here.</a>
 */
class EdgePriorityQueue {

  private int size = 0;
  private int capacity;
  private Edge[] heap;

  /**
   * Create a new {@link EdgePriorityQueue} with an initial queue capacity
   *
   * @param initialCapacity Capacity that the queue should be initialized to
   */
  EdgePriorityQueue(int initialCapacity) {
    capacity = initialCapacity;
    heap = new Edge[capacity];
  }

  /**
   * Add an {@link Edge} to the priority queue
   *
   * @param e prim.Edge to be added to the queue
   */
  void add(Edge e) {
    checkCapacity(); // Make sure heap array is large enough
    heap[size] = e;  // Put the new edge at the end of the heap
    size++;          // Increase the size
    heapUp();        // Heap new prim.Edge upward until heap property is satisfied
  }

  /**
   * Remove the least cost edge from the queue
   *
   * @return prim.Edge with the least cost
   */
  Edge removeLeast() {
    // If queue is empty, throw exception.
    if (size == 0)
      throw new IllegalStateException();
    Edge edge = heap[0];      // Get the edge at the top of the heap
    heap[0] = heap[size - 1]; // Set last element to first element
    size--;                   // Decrement size
    heapDown();     // Move first element down until heap property is satisfied
    return edge;              // Return the lowest cost edge
  }

  /**
   * Check if the queue is empty
   *
   * @return True if queue is empty. False otherwise.
   */
  boolean isEmpty() {
    return size == 0;
  }

  /**
   * Swap two edges in the queue
   *
   * @param indexOne Index of first prim.Edge to swap
   * @param indexTwo Index of second prim.Edge to swap
   */
  private void swap(int indexOne, int indexTwo) {
    Edge temp = heap[indexOne]; // Save the current value at indexOne
    heap[indexOne] = heap[indexTwo]; // Set indexOne value to indexTwo value
    heap[indexTwo] = temp;  // Set indexTwo value to temp value
  }

  /**
   * Check the current capacity of the queue. If the queue's size is equal to
   * its capacity, it creates a clone of the current queue and doubles its size
   */
  private void checkCapacity() {
    // If the queue is at capacity double the queue's size
    if (size == capacity) {
      heap = Arrays.copyOf(heap, capacity * 2);
      capacity *= 2;
    }
  }

  /**
   * Heap upward to ensure that the heap property is maintained. In this
   * case, the least cost edges should be at the head of the queue.
   */
  private void heapUp() {
    int index = size - 1; // Set index to the last element of heap
    // Loop until the heap is satisfied
    while (hasParent(index) &&
        getParent(index).getEdgeWeight() > heap[index].getEdgeWeight()) {
      // Swap the index value to its parent until its weight is smaller than
      // its parent
      swap(getParentIndex(index), index);
      index = getParentIndex(index);
    }
  }

  /**
   * Heap downward until the queue property is satisfied. In this case, the
   * least cost edges should be at the head of the queue.
   */
  private void heapDown() {
    int index = 0; // Set index to first element of the heap

    // Loop until the node has no other left child
    while (hasLeftChild(index)) {
      // Get index of left child
      int smallerChildIndex = getLeftChildIndex(index);
      // Check if weight of left child is smaller than the weight of the right
      if (hasRightChild(index) &&
          getRightChild(index).getEdgeWeight() < getLeftChild(index).getEdgeWeight()) {
        // Set smaller child to the right index instead
        smallerChildIndex = getRightChildIndex(index);
      }

      // Check if the weight of current index is smaller than its smallest child
      if (heap[index].getEdgeWeight() < heap[smallerChildIndex].getEdgeWeight()) {
        break; // Heap is in proper heap order
      } else {
        // Swap with smaller child if not
        swap(index, smallerChildIndex);
      }
      // Set the index to the smaller child before looping again
      index = smallerChildIndex;
    }
  }

  //============================================================================
  // Binary Heap Helper Methods
  //============================================================================

  /**
   * Get the index of the given index's left child
   *
   * @param index index of the prim.Edge to get the left child of
   * @return index of left child
   */
  private int getLeftChildIndex(int index) {
    return 2 * index + 1;
  }

  /**
   * Get the index of the given index's right child
   *
   * @param index index of the prim.Edge to get the right child of
   * @return index of right child
   */
  private int getRightChildIndex(int index) {
    return 2 * index + 2;
  }

  /**
   * Get the index of the given index's parent
   *
   * @param index index of the prim.Edge to get the parent of
   * @return index of parent child
   */
  private int getParentIndex(int index) {
    return (index - 1) / 2;
  }

  /**
   * Check if the given index has a parent
   *
   * @param index index of the prim.Edge to check if parent exists
   * @return True if the index has a parent. False if not.
   */
  private boolean hasParent(int index) {
    return getParentIndex(index) >= 0;
  }

  /**
   * Check if the given index has a left child
   *
   * @param index index of the prim.Edge to check if left child exists
   * @return True if index has a left child. False if not.
   */
  private boolean hasLeftChild(int index) {
    return getLeftChildIndex(index) < size;
  }

  /**
   * Check if the given index has a right child
   *
   * @param index index of the prim.Edge to check if right child exists
   * @return True if index has a right child. False if not.
   */
  private boolean hasRightChild(int index) {
    return getRightChildIndex(index) < size;
  }

  /**
   * Get the left child of the given index
   *
   * @param index index of the prim.Edge to get the left child of
   * @return prim.Edge that is the left child
   */
  private Edge getLeftChild(int index) {
    return heap[getLeftChildIndex(index)];
  }

  /**
   * Get the right child of the given index
   * @param index index of the prim.Edge to get the right child of
   * @return prim.Edge that is the right child
   */
  private Edge getRightChild(int index) {
    return heap[getRightChildIndex(index)];
  }

  /**
   * Get the parent of the given index
   * @param index index of the prim.Edge to get the parent of
   * @return prim.Edge that is the parent
   */
  private Edge getParent(int index) {
    return heap[getParentIndex(index)];
  }

  /**
   * Output the priority queue as a String
   * @return String representation of the priority queue
   */
  @Override
  public String toString() {
    return Arrays.toString(heap);
  }
}
