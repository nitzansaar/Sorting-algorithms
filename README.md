# Sorting Algorithms Implementation

This project provides a Java implementation of various sorting algorithms, including insertion sort, shaker sort, randomized quicksort, hybrid sort, and a unique sorting method. The algorithms are designed to efficiently sort arrays of elements and cover a wide range of use cases.

## Sorting Algorithms Implemented

### Insertion Sort

The `insertionSort` method implements the insertion sort algorithm. It sorts a sublist of an array using the insertion technique, where each element is inserted at the correct position within the sorted section of the array.

### Shaker Sort

The `shakerSort` method implements the shaker sort algorithm. It repeatedly traverses the array in both directions, swapping adjacent elements that are out of order until the entire sublist is sorted.

### Randomized Quick Sort

The `randomizedQuickSort` method implements the randomized quicksort algorithm. It uses a pivot element to partition the array into two subarrays, recursively sorting each subarray.

### Hybrid Sort

The `hybridSort` method combines insertion sort and randomized quicksort to implement the hybrid sort algorithm. It switches to insertion sort when the sublist size is below a specified threshold and uses randomized quicksort otherwise.

### Bucket Sort

The `bucketSort` method performs bucket sort on a sub-array of records. It determines the range of key values, divides the range into buckets, and distributes records into the appropriate buckets. Finally, the sorted elements are merged back into the original array.

### Unique Sorting Algorithm

The project includes a custom sorting algorithm that efficiently sorts an array of votes representing candidate preferences. The algorithm runs in linear time and uses only a constant amount of extra memory. It involves two passes over the array, swapping elements to determine the winner of an election.

### Shell Sort (Additional)

An additional sorting algorithm, `shellSort`, has been implemented using the Shell sort technique. It involves dividing the array into subarrays and sorting them using insertion sort. The algorithm iteratively reduces the gap between elements to sort the entire array efficiently.

## How to Use

1. Compile the Java files using a Java compiler (e.g., `javac SortingImplementation.java`).
2. Run the application using the Java Virtual Machine (e.g., `java SortingImplementation`).
3. Follow the prompts to select the sorting algorithm and provide input data.
4. View the sorted output and performance statistics.

## What I Learned

- Implementing various sorting algorithms and understanding their complexities.
- Applying different sorting techniques to efficiently arrange elements in ascending or descending order.
- Handling various types of data and optimizing sorting for different scenarios.
- Utilizing recursive algorithms for quicksort and hybrid sort.
- Designing a custom sorting algorithm that solves a specific problem efficiently.
- Practicing good coding practices such as modularization, commenting, and method documentation.


