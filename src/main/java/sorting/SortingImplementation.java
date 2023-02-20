package sorting;
import java.io.*;
import java.util.Arrays;
import java.util.Random;
/**  A class that implements SortingInterface. Contains methods
 *   that sort a list of elements. */
public class SortingImplementation  implements SortingInterface {

    private static final int THRESHOLD = 11;

    private static void swap(Comparable[] array, int i, int j) {
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    private static Random rand = new Random();

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
            while(i <= j && array[i].compareTo(pivotElem) < 0) {
                i++;
            }
            // decrement right until element is smaller than pivot
            while(i <= j && array[j].compareTo(pivotElem) >= 0) {
                j--;
            }
            // swap if i & j are stuck and i < j
            if(i < j){
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

    /**
     * Sorts the sublist of the given list (from lowindex to highindex)
     * using insertion sort
     * @param array array of Comparable-s
     * @param lowindex the beginning index of a sublist
     * @param highindex the end index of a sublist
     * @param reversed if true, the list should be sorted in descending order
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
            }else {
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
     *  using the shaker sort (see pdf for description)
     * @param array array of Comparable-s
     * @param lowindex the beginning index of a sublist
     * @param highindex the end index of a sublist
     * @param reversed if true, the list should be sorted in descending order
     */
    public void shakerSort(Comparable[] array, int lowindex, int highindex, boolean reversed) {
        while(lowindex < highindex) {
            if (reversed){
                for (int i = lowindex; i < highindex; i++) {
                    if (array[i].compareTo(array[i + 1]) < 0) {
                        swap(array, i, i + 1);
                    }
                }
                highindex--;
                for (int j = highindex; j > lowindex; j--) {
                    if (array[j].compareTo(array[j - 1]) > 0) {
                        swap(array, j, j-1);
                    }
                }
                lowindex++;
            }else{
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
                        swap(array, j, j-1);
                    }
                }
                lowindex++;
            }
        }

    }



    /**
     * Sorts the sublist of the given list (from lowindex to highindex)
     * using the randomizedQuickSort
     * @param array array to sort
     * @param lowindex the beginning index of a sublist
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
     * @param array array of Comparable-s to sort
     * @param lowindex the beginning index of the sublist
     * @param highindex the end index of the sublist
     */
    @Override
    public void hybridSort(Comparable[] array, int lowindex, int highindex) {
        if (lowindex < highindex) {
            if ((highindex - lowindex + 1) <= THRESHOLD){
                insertionSort(array, lowindex, highindex, false);
            }else {
                int pivotIndex = partition(array, lowindex, highindex);
                hybridSort(array, lowindex, pivotIndex - 1);
                hybridSort(array, pivotIndex + 1, highindex);
            }
        }
    }

    /**
     * Sorts a sub-array of records using bucket sort.
     * @param array array of records
     * @param lowindex the beginning index of the sublist to sort
     * @param highindex the end index of the sublist to sort
     * @param reversed if true, sort in descending (decreasing) order, otherwise ascending
     */
    @Override
    public void bucketSort(Elem[] array, int lowindex, int highindex, boolean reversed) {
        // FILL IN CODE

    }

    /**
     * Sorts a sub-array of integers using a radix sort (you may use any base). You may
     * assume that all elements of the array have the same # of digits.
     * @param array array of records
     * @param lowindex the beginning index of the sublist to sort
     * @param highindex the end index of the sublist to sort
     * @param reversed if true, sort in descending (decreasing) order, otherwise ascending
     */
    @Override
    public void radixSort(int[] array, int lowindex, int highindex, boolean reversed) {
        // FILL IN CODE

    }

    private void breakUpFile(String filename, int chunkSize, int numChunks) throws IOException {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        Comparable[] chunks = new Comparable[chunkSize];
        String line;
        int count = 0;
        int numFiles = 0;
        while ((line = br.readLine()) != null) {
            chunks[count++] = Integer.parseInt(line);
            if (count == chunkSize) {
                // sort the chunks using hybridSort
                hybridSort(chunks, 0, chunks.length - 1);
                // store sorted array into temp file
                String tempFileName = "temp" + (numFiles++) + ".txt";
                File tempFile = new File(tempFileName);
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
                for (int i = 0; i < chunkSize; i++) {
                    bw.write(String.valueOf(chunks[i]));
                    bw.newLine();
                }
                bw.close();
                count = 0;
            }
        }
        // clean up leftovers
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

    /**
     * Implements external sort method
     * @param inputFile The file that contains the input list
     * @param outputFile The file where to output the sorted list
     * @param k number of elements that fit into memory at once
     * @param m number of chunks
     */
    public void externalSort(String inputFile, String outputFile, int k, int m) {
        try {
            breakUpFile(inputFile, k, m);
            BufferedReader[] bufferedReaders = new BufferedReader[m];
            for (int i = 0; i < m; i++) {
                String fileName = "temp" + i + ".txt";
                File file = new File(fileName);
                try {
                    bufferedReaders[i] = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
                    System.out.println("File doesn't exist: " + fileName);// need to revise
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
                    elements[i] = Integer.MAX_VALUE;
                }
            }

        } catch (IOException e) {
            System.out.println("File doesn't exist: " + inputFile);
            throw new RuntimeException(e);
        }


    }

    /**
     * Suppose some city has n people, and these people need to vote to select a mayor of the city. There are three candidates for a mayor: "A",  "B" and "C".  We are given an array of n Strings where each element represents a vote for either candidate "A" or candidate "B", or candidate "C". For the purpose of this problem, let's assume there is a clear winner (so one candidate has more votes than the other two).
     * Design and implement (in Java) an in-place algorithm for sorting this array and determining who wins the election, "A", "B" or "C".
     * Example: if we are given the following array that represents votes of 11 people:
     *             ["A", "B", "A", "C", "A", "A", "A", "B", "C", "A", "B"],
     *     your method should return "A" and change the array so that it is sorted:
     *             ["A", "A", "A", "A", "A", "A", "B", "B", "B",  "C", "C"]
     *  The algorithm must satisfy the following requirements:
        - Use the variation of the partition method of quicksort)
        - Should run in linear time
        - Use no extra memory (except for two integer indices and a tmp variable for swapping).
        - Run in two passes
     * Important: Do NOT just iterate over the array and count the number of "A"s, "B"s and "C"s  - such solutions will get 0 points.
     * Do NOT use counting sort.
     *
     * @param votes input array of votes
     * @return winner
     */
    public String sortAndFindWinner (String[] votes) {
        // FILL IN CODE

        return ""; // replace
    }


    // FILL IN CODE:
    // Research and implement one more sorting method that we did not discuss in class.
    // Do not copy code from the web. Implement the algorithm yourself.
    // Describe it in a Readme file.

}