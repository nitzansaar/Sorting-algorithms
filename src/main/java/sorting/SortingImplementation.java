package sorting;

import java.io.*;
import java.util.*;

/**
 * A class that implements SortingInterface. Contains methods
 * that sort a list of elements.
 */
public class SortingImplementation implements SortingInterface {

    private static final int THRESHOLD = 10;


    /**
     * Sorts the sublist of the given list (from lowindex to highindex)
     * using insertion sort
     *
     * @param array     array of Comparable-s
     * @param lowindex  the beginning index of a sublist
     * @param highindex the end index of a sublist
     * @param reversed  if true, the list should be sorted in descending order
     */
    @Override
    public void insertionSort(Comparable[] array, int lowindex, int highindex, boolean reversed) {
        for (int i = lowindex + 1; i <= highindex; i++) {
            Comparable curr = array[i];
            int j = i - 1;
            //stop at lowindex because we are only sorting from low -> high
            if (reversed) {
                while (j >= lowindex && array[j].compareTo(curr) < 0) {
                    array[j + 1] = array[j];
                    j--;
                }
            } else {
                while (j >= lowindex && array[j].compareTo(curr) > 0) {
                    array[j + 1] = array[j];
                    j--;
                }
            }
            //put the curr at the correct position
            array[j + 1] = curr;
        }
    }

    /**
     * Sorts the sublist of the given list (from lowindex to highindex)
     * using the shaker sort (see pdf for description)
     *
     * @param array     array of Comparable-s
     * @param lowindex  the beginning index of a sublist
     * @param highindex the end index of a sublist
     * @param reversed  if true, the list should be sorted in descending order
     */
    public void shakerSort(Comparable[] array, int lowindex, int highindex, boolean reversed) {
        while (lowindex < highindex) {
            if (reversed) {
                for (int i = lowindex; i < highindex; i++) {
                    if (array[i].compareTo(array[i + 1]) < 0) {
                        swap(array, i, i + 1);
                    }
                }
                highindex--;
                for (int j = highindex; j > lowindex; j--) {
                    if (array[j].compareTo(array[j - 1]) > 0) {
                        swap(array, j, j - 1);
                    }
                }
                lowindex++;
            } else {
                // bubble max to top
                for (int i = lowindex; i < highindex; i++) {
                    if (array[i].compareTo(array[i + 1]) > 0) {
                        swap(array, i, i + 1);
                    }
                }
                highindex--;
                // bubble min to bottom
                for (int j = highindex; j > lowindex; j--) {
                    if (array[j].compareTo(array[j - 1]) < 0) {
                        swap(array, j, j - 1);
                    }
                }
                lowindex++;
            }
        }

    }


    /**
     * Sorts the sublist of the given list (from lowindex to highindex)
     * using the randomizedQuickSort
     *
     * @param array     array to sort
     * @param lowindex  the beginning index of a sublist
     * @param highindex the end index of a sublist
     */
    @Override
    public void randomizedQuickSort(Comparable[] array, int lowindex, int highindex) {
        if (lowindex < highindex) {
            int pivotIndex = partition(array, lowindex, highindex);
            randomizedQuickSort(array, lowindex, pivotIndex - 1);
            randomizedQuickSort(array, pivotIndex + 1, highindex);
        }

    }

    /**
     * Sorts a given sublist using hybrid sort that combines insertion sort and randomized quick sort.
     * See pdf for details.
     *
     * @param array     array of Comparable-s to sort
     * @param lowindex  the beginning index of the sublist
     * @param highindex the end index of the sublist
     */
    @Override
    public void hybridSort(Comparable[] array, int lowindex, int highindex) {
        if (lowindex < highindex) {
            if ((highindex - lowindex + 1) <= THRESHOLD) {
                insertionSort(array, lowindex, highindex, false);
            } else {
                int pivotIndex = partition(array, lowindex, highindex);
                hybridSort(array, lowindex, pivotIndex - 1);
                hybridSort(array, pivotIndex + 1, highindex);
            }
        }
    }


    /**
     * Sorts a sub-array of records using bucket sort.
     *
     * @param array     array of records
     * @param lowindex  the beginning index of the sublist to sort
     * @param highindex the end index of the sublist to sort
     * @param reversed  if true, sort in descending (decreasing) order, otherwise ascending
     */
    @Override
    public void bucketSort(Elem[] array, int lowindex, int highindex, boolean reversed) {

        // find min and max key values to determine range
        int max = getMax(array, lowindex, highindex);
        int min = getMin(array, lowindex, highindex);
        int range = max - min + 1;

        int numBuckets = (int) Math.ceil((highindex - lowindex + 1) / 2.0);
        int bucketSize = (int) Math.ceil((double) range / numBuckets);

        LinkedList<Elem>[] buckets = new LinkedList[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new LinkedList<>();
        }

        // put keys into correct bucket
        for (int i = lowindex; i <= highindex; i++) {
            int bucketIndex = (array[i].key() - min) / bucketSize;
            insertSorted(buckets[bucketIndex], array[i], reversed);
        }
        int insertIndex = lowindex;
        if (!reversed) {
            for (int i = 0; i < numBuckets; i++) {
                LinkedList<Elem> currentBucket = buckets[i];
                for (Elem elem : currentBucket) {
                    array[insertIndex++] = elem;
                }
            }
        } else {
            for (int i = numBuckets - 1; i >= 0; i--) {
                LinkedList<Elem> currentBucket = buckets[i];
                for (Elem elem : currentBucket) {
                    array[insertIndex++] = elem;
                }
            }
        }
    }

    /**
     * Sorts a sub-array of integers using a radix sort (you may use any base). You may
     * assume that all elements of the array have the same # of digits.
     *
     * @param array     array of records
     * @param lowindex  the beginning index of the sublist to sort
     * @param highindex the end index of the sublist to sort
     * @param reversed  if true, sort in descending (decreasing) order, otherwise ascending
     */
    @Override
    public void radixSort(int[] array, int lowindex, int highindex, boolean reversed) {
        // FILL IN CODE

    }

    /**
     * Implements external sort method
     *
     * @param inputFile  The file that contains the input list
     * @param outputFile The file where to output the sorted list
     * @param k          number of elements that fit into memory at once
     * @param m          number of chunks
     */
    public void externalSort(String inputFile, String outputFile, int k, int m) {
        try {
            File output = new File(outputFile);
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(output));
            parseFile(inputFile, k, m);
            BufferedReader[] bufferedReaders = new BufferedReader[m];
            for (int i = 0; i < m; i++) {
                String fileName = "temp" + i + ".txt";
                File file = new File(fileName);
                try {
                    bufferedReaders[i] = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
                    System.out.println("Invalid File: " + fileName);// need to revise
                    throw new RuntimeException(e);
                }
            }
            // store the first element from each file into an array
            int[] elements = new int[m];
            for (int i = 0; i < m; i++) {
                String line = bufferedReaders[i].readLine();
                if (line != null) {
                    elements[i] = Integer.parseInt(line);
                } else {
                    // we write infinity to note that the file is empty
                    elements[i] = Integer.MAX_VALUE;
                }
            }

            // runs until we reach a break statement
            while (true) {
                // now we need to determine the min element and its index
                int min = Integer.MAX_VALUE;
                int minIndex = -1;
                for (int i = 0; i < m; i++) {
                    if (elements[i] < min) {
                        min = elements[i];
                        minIndex = i;
                    }
                }
                // if minIndex is -1 we know that all values being read are infinity and we can break
                if (minIndex == -1) {
                    break;
                }
                // now we need to write the min element to the output file
                outputWriter.write(String.valueOf(min));
                outputWriter.newLine();

                // read next line from bufferedReader at the min index and replace it with
                // the min element we just wrote to output file
                String line = bufferedReaders[minIndex].readLine();
                if (line != null) {
                    elements[minIndex] = Integer.parseInt(line);
                } else {
                    elements[minIndex] = Integer.MAX_VALUE;
                }
            }
            outputWriter.close();
        } catch (IOException e) {
            System.out.println("File doesn't exist: " + inputFile);
            throw new RuntimeException(e);
        }
    }

    /**
     * Suppose some city has n people, and these people need to vote to select a mayor of the city. There are three candidates for a mayor: "A",  "B" and "C".  We are given an array of n Strings where each element represents a vote for either candidate "A" or candidate "B", or candidate "C". For the purpose of this problem, let's assume there is a clear winner (so one candidate has more votes than the other two).
     * Design and implement (in Java) an in-place algorithm for sorting this array and determining who wins the election, "A", "B" or "C".
     * Example: if we are given the following array that represents votes of 11 people:
     * ["A", "B", "A", "C", "A", "A", "A", "B", "C", "A", "B"],
     * your method should return "A" and change the array so that it is sorted:
     * ["A", "A", "A", "A", "A", "A", "B", "B", "B",  "C", "C"]
     * The algorithm must satisfy the following requirements:
     * - Use the variation of the partition method of quicksort)
     * - Should run in linear time
     * - Use no extra memory (except for two integer indices and a tmp variable for swapping).
     * - Run in two passes
     * Important: Do NOT just iterate over the array and count the number of "A"s, "B"s and "C"s  - such solutions will get 0 points.
     * Do NOT use counting sort.
     *
     * @param votes input array of votes
     * @return winner
     */
    public String sortAndFindWinner(String[] votes) {
        int i = 0;
        int j = votes.length - 1;
        // move 'A' to the front of the array
        // O(n)
        while (i <= j) {
            // decrement j until we get to 'A'
            if (votes[j].equals("B") || votes[j].equals("C")) {
                j--;
            }
            // once we are at 'A', swap and increment i
            else {
                swap(votes, i++, j);
            }
        }
        int aIndex = i;
        j = votes.length - 1;
        // O(n)
        while (i <= j) {
            while (i < j && votes[i].equals("B")) {
                i++;
            }
            while (i < j && votes[j].equals("C")) {
                j--;
            }
            swap(votes, i++, j--);
        }
        if (aIndex > (i - aIndex) && aIndex > (votes.length - i)) {
            return "A";
        } else if ((i - aIndex) > aIndex && (i - aIndex) > (votes.length - i)) {
            return "B";
        }
        return "C";

    }


    // FILL IN CODE:
    // Research and implement one more sorting method that we did not discuss in class.
    // Do not copy code from the web. Implement the algorithm yourself.
    // Describe it in a Readme file.
    private static void swap(Comparable[] array, int i, int j) {
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static Random rand = new Random();

    /*
    Helper method for randomized quick sort
     */
    private static int partition(Comparable[] array, int lowindex, int highindex) {
        // get 3 random indices in the array
        int[] indices = {rand.nextInt(highindex - lowindex + 1) + lowindex,
                rand.nextInt(highindex - lowindex + 1) + lowindex,
                rand.nextInt(highindex - lowindex + 1) + lowindex};
        int pivotIndex = getMedianIndex(array, indices[0], indices[1], indices[2]);
        Comparable pivotElem = array[pivotIndex];
        // swap pivot with last element
        swap(array, pivotIndex, highindex);
        int i = lowindex;
        int j = highindex;

        while (i <= j) {
            // increment left until element is greater than pivot
            while (i <= j && array[i].compareTo(pivotElem) < 0) {
                i++;
            }
            // decrement right until element is smaller than pivot
            while (i <= j && array[j].compareTo(pivotElem) >= 0) {
                j--;
            }
            // swap if i & j are stuck and i < j
            if (i < j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        // put the pivot in the correct position
        swap(array, i, highindex);
        // now everything to left of pivot is smaller and everything to the right is bigger
        return i;
    }

    /*
    Helper method for randomized quick sort
     */
    private static int getMedianIndex(Comparable[] arr, int a, int b, int c) {
        if (arr[a].compareTo(arr[b]) < 0) {
            if (arr[b].compareTo(arr[c]) < 0) {
                return b;
            } else if (arr[a].compareTo(arr[c]) < 0) {
                return c;
            } else {
                return a;
            }
        } else {
            if (arr[a].compareTo(arr[c]) < 0) {
                return a;
            } else if (arr[b].compareTo(arr[c]) < 0) {
                return c;
            } else {
                return b;
            }
        }

    }

    /*
    Helper method for external sort
     */
    private void parseFile(String filename, int chunkSize, int numChunks) throws IOException {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        Comparable[] chunks = new Comparable[chunkSize];
        String line;
        int count = 0;
        int numFiles = 0;
        while ((line = br.readLine()) != null) {
            chunks[count++] = Integer.parseInt(line);
            // if count = chunkSize, we have k elements in the array and need to stop adding elements
            if (count == chunkSize) {
                // sort the chunks using hybridSort (can use any sorting algorithm)
                hybridSort(chunks, 0, chunks.length - 1);
                // store sorted array into temp file
                String tempFileName = "temp" + (numFiles++) + ".txt";
                File tempFile = new File(tempFileName);
                // create new buffered writer for temp file
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
                for (int i = 0; i < chunkSize; i++) {
                    bw.write(String.valueOf(chunks[i]));
                    // add new line because we want each element on a separate line
                    bw.newLine();
                }
                bw.close();
                //reset count
                count = 0;
            }
        }

        // if numFiles is less than numChunks, it means there are still a few elements
        // in the file (less than k)
        if (numFiles < numChunks) {
            Comparable[] leftovers = new Comparable[count];
            for (int i = 0; i < count; i++) {
                leftovers[i] = chunks[i];
            }
            hybridSort(leftovers, 0, leftovers.length - 1);
            String tempFileName = "temp" + numFiles + ".txt";
            File tempFile = new File(tempFileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            for (int i = 0; i < count; i++) {
                bw.write(String.valueOf(leftovers[i]));
                bw.newLine();
            }
            bw.close();
        }
        br.close();
    }

    /*
    Helper Method for BucketSort
     */
    private void insertSorted(LinkedList<Elem> bucket, Elem elem, boolean reversed) {
        ListIterator<Elem> it = bucket.listIterator();
        while (it.hasNext()) {
            Elem curr = it.next();
            // place the element in the sorted position
            if ((reversed && elem.key() > curr.key()) || (!reversed && elem.key() < curr.key())) {
                it.previous();
                it.add(elem);
                return;
            }
        }
        // if we get to here it means that the element is the largest in the linkedlist and we add it to the end
        it.add(elem);
    }
    private static int getMax(Elem[] arr, int low, int high) {
        int max = arr[low].key();
        for (int i = low + 1; i <= high; i++) {
            int current = arr[i].key();
            if (current > max) {
                max = current;
            }
        }
        return max;
    }
    private static int getMin(Elem[] arr, int low, int high) {
        int min = arr[low].key();
        for (int i = low + 1; i <= high ; i++) {
            int current = arr[i].key();
            if (current < min) {
                min = current;
            }
        }
        return min;
    }

}