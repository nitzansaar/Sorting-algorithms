package sorting;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Nitzan_Tests extends SortingImplementation {

    /*
    This is the test method I used to compare the efficiency of Randomized Quick Sort and Hybrid Sort
    described in the README_hybridSort
     */
    @Test
    void testHybridAndQuickSort() {
        int[] sizes = {10, 100, 1000, 10000, 100000, 1000000, 10000000};
        for (int size : sizes) {
            Integer[] quickArr1 = generateRandomArray(size);
            Integer[] quickArr2 = generateSortedArray(size, true);
            Integer[] quickArr3 = generateSortedArray(size, false);
            // want to test on the same arrays
            Integer[] hybridArr1 = quickArr1;
            Integer[] hybridArr2 = quickArr2;
            Integer[] hybridArr3 = quickArr3;

            long quickStart1 = System.nanoTime();
            randomizedQuickSort(quickArr1, 0, quickArr1.length - 1);
            long quickEnd1 = System.nanoTime();

            long quickStart2 = System.nanoTime();
            randomizedQuickSort(quickArr2, 0, quickArr1.length - 1);
            long quickEnd2 = System.nanoTime();

            long quickStart3 = System.nanoTime();
            randomizedQuickSort(quickArr3, 0, quickArr1.length - 1);
            long quickEnd3 = System.nanoTime();

            long hybridStart1 = System.nanoTime();
            hybridSort(hybridArr1, 0, hybridArr1.length - 1);
            long hybridEnd1 = System.nanoTime();

            long hybridStart2 = System.nanoTime();
            hybridSort(hybridArr2, 0, hybridArr1.length - 1);
            long hybridEnd2 = System.nanoTime();

            long hybridStart3 = System.nanoTime();
            hybridSort(hybridArr3, 0, hybridArr1.length - 1);
            long hybridEnd3 = System.nanoTime();

            double randQuickTime = (double) (quickEnd1 - quickStart1) / 1_000_000_000.0;
            double randHybridTime = (double) (hybridEnd1 - hybridStart1) / 1_000_000_000.0;

            double sortedQuickTime = (double) (quickEnd2 - quickStart2) / 1_000_000_000.0;
            double sortedHybridTime = (double) (hybridEnd2 - hybridStart2) / 1_000_000_000.0;

            double inverseQuickTime = (double) (quickEnd3 - quickStart3) / 1_000_000_000.0;
            double inverseHybridTime = (double) (hybridEnd3 - hybridStart3) / 1_000_000_000.0;

            System.out.println("Array size: " + size);
            System.out.println("Array of random numbers:");
            if (randHybridTime < randQuickTime) {
                System.out.println("Hybrid is faster by: " + (randQuickTime - randHybridTime) + "s");
            } else {
                System.out.println("Quick is faster by: " + (randHybridTime - randQuickTime) + "s");
            }
            System.out.println("Array of sorted numbers:");
            if (sortedHybridTime < sortedQuickTime) {
                System.out.println("Hybrid is faster by: " + (sortedQuickTime - sortedHybridTime) + "s");
            } else {
                System.out.println("Quick is faster by: " + (sortedHybridTime - sortedQuickTime) + "s");
            }
            System.out.println("Array of inverse numbers:");
            if (inverseHybridTime < inverseQuickTime) {
                System.out.println("Hybrid is faster by: " + (inverseQuickTime - inverseHybridTime) + "s");
            } else {
                System.out.println("Quick is faster by: " + (inverseHybridTime - inverseQuickTime) + "s");
            }
            System.out.println();
            assertTrue(isSorted(quickArr1));
            assertTrue(isSorted(hybridArr1));
            assertTrue(isSorted(quickArr2));
            assertTrue(isSorted(hybridArr2));
            assertTrue(isSorted(quickArr3));
            assertTrue(isSorted(hybridArr3));
        }
    }

    @Test
    void testInsertionSort() {
        System.out.println("\nTesting Insertion Sort");
        Integer arr[] = generateRandomArray(10);
        insertionSort(arr, 0, arr.length - 1, false);
        assertTrue(isSorted(arr));
        System.out.println(Arrays.toString(arr));
        insertionSort(arr, 0, arr.length - 1, true);
        assertTrue(isSortedDescending(arr));
        System.out.println(Arrays.toString(arr));
    }

    @Test
    void testRandomizedQuickSort() {
        System.out.println("\nRandomized Quick Sort");
        Integer arr[] = generateRandomArray(10);
        randomizedQuickSort(arr, 0, arr.length - 1);
        assertTrue(isSorted(arr));
        System.out.println(Arrays.toString(arr));

    }

    @Test
    void testShakerSort() {
        System.out.println("Testing Shaker Sort");
        Integer arr[] = generateRandomArray(10);
        shakerSort(arr, 0, arr.length - 1, false);
        assertTrue(isSorted(arr));
        System.out.println(Arrays.toString(arr));
        shakerSort(arr, 0, arr.length - 1, true);
        System.out.println(Arrays.toString(arr));
        assertTrue(isSortedDescending(arr));
    }

    @Test
    void testExternalSort() {
        externalSort("testFile", "testFile2", 2, 11);
    }

    @Test
    void testSortAndFindWinner() {
        System.out.println("\nTesting Sort & Find Winner");
        String[] arr = {"C", "C", "C", "B", "A", "A", "A", "C", "B", "B", "A", "B", "B", "A", "C", "C", "C", "C", "B", "B" };
        String winner = sortAndFindWinner(arr);
        assertTrue(isSorted(arr));
        assertTrue(winner.equals("C"));
        System.out.println(Arrays.toString(arr));
    }

    @Test
    void testBucketSort() {
        System.out.println("\nTesting Bucket Sort");
        Elem arr[] = {new Elem(114, "Dog"), new Elem(26, "Owl"), new Elem(50, "Hamster"),
                new Elem(155, "Cat"), new Elem(44, "squirrel"), new Elem(111, "fish"), new Elem(4, "monkey"),
                new Elem(95, "lobster"), new Elem(151, "snake"), new Elem(200, "elephant"), new Elem(12, "bear")};
        bucketSort(arr, 0, arr.length - 1, false);
        System.out.println(Arrays.toString(arr));
        bucketSort(arr, 0, arr.length - 1, true);
        System.out.println(Arrays.toString(arr));
    }

    @Test
    void testShellSort() {
        Integer arr[] = generateRandomArray(1000);
        shellSort(arr);
        assertTrue(isSorted(arr));
    }
    private static boolean isSortedDescending(Comparable[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[i - 1]) > 0) {
                return false;
            }
        }
        return true;
    }
    private static boolean isSorted(Comparable[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].compareTo(arr[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    private static Integer[] generateRandomArray(int size) {
        Integer[] arr = new Integer[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(size);
        }
        return arr;
    }

    private static Integer[] generateSortedArray(int size, boolean ascending) {
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            // if ascending is true -> i , if false -> size - i
            arr[i] = ascending ? i : size - i;
        }
        return arr;
    }

}