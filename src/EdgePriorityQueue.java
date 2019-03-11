import java.util.Arrays;

class EdgePriorityQueue {

  private int size = 0;
  private int capacity;
  private Edge[] heap = null ;

  EdgePriorityQueue(int initialCapacity) {
    capacity = initialCapacity;
    heap = new Edge[capacity];
    System.out.println(heap.length);
  }

  void add(Edge e) {
    checkCapacity();
    heap[size] = e;
    size++;
    heapUp();
  }

  Edge removeLeast() {
    if (size == 0) throw new IllegalStateException();
    Edge edge = heap[0];
    heap[0] = heap[size - 1]; // Set last element to first element
    size--;
    heapDown();
    return edge;
  }

  boolean isEmpty() {
    return size == 0;
  }

  private void swap(int indexOne, int indexTwo) {
    Edge temp = heap[indexOne];
    heap[indexOne] = heap[indexTwo];
    heap[indexTwo] = temp;
  }

  private void checkCapacity() {
    if (size == capacity) {
      heap = Arrays.copyOf(heap, capacity * 2);
      capacity *= 2;
    }
  }

  private void heapUp() {
    int index = size - 1;
    while (hasParent(index) &&
        getParent(index).getEdgeWeight() > heap[index].getEdgeWeight()) {
      swap(getParentIndex(index), index);
      index = getParentIndex(index);
    }
  }

  private void heapDown() {
    int index = 0;
    while (hasLeftChild(index)) {
      int smallerChildIndex = getLeftChildIndex(index);
      if (hasRightChild(index)
          && getRightChild(index).getEdgeWeight() < getLeftChild(index).getEdgeWeight()) {
        smallerChildIndex = getRightChildIndex(index);
      }

      if (heap[index].getEdgeWeight() < heap[smallerChildIndex].getEdgeWeight()) {
        break; // Heap is in proper heap order
      } else {
        swap(index, smallerChildIndex);
      }
      index = smallerChildIndex;
    }
  }

  // Binary Heap Helper Methods
  private int getLeftChildIndex(int index) {
    return 2 * index + 1;
  }

  private int getRightChildIndex(int index) {
    return 2 * index + 2;
  }

  private int getParentIndex(int index) {
    return (index - 1) / 2;
  }

  private boolean hasParent(int index) {
    return getParentIndex(index) >= 0;
  }

  private boolean hasLeftChild(int index) {
    return getLeftChildIndex(index) < size;
  }

  private boolean hasRightChild(int index) {
    return getRightChildIndex(index) < size;
  }

  private Edge getLeftChild(int index) {
    return heap[getLeftChildIndex(index)];
  }

  private Edge getRightChild(int index) {
    return heap[getRightChildIndex(index)];
  }

  private Edge getParent(int index) {
    return heap[getParentIndex(index)];
  }

  @Override
  public String toString() {
    return Arrays.toString(heap);
  }
}
