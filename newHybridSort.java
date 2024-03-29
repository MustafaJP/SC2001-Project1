
import java.io.IOException;
import java.util.*;

public class newHybridSort {

    protected static int[] slot;
    private static int S = 100; // threshold for switching to insert
    static int keyComp;
    static final int TEST_VALUE = 1000;

    public newHybridSort(int[] array) {
        // Initialize the slot array with a copy of the input array
        if (slot == null) {
            slot = Arrays.copyOf(array, array.length);
        } else {
            System.arraycopy(array, 0, slot, 0, array.length);
        }
    }

    public static int[] generateRandom(int size, int max) {
        // Generate an array of random integers with the specified size and maximum
        // value
        return new Random().ints(size, 0, max).toArray();
    }

    public static void hybridSorter(int[] arr, int leftIndex, int rightIndex) {
        // Recursive function to perform hybrid sorting

        // Calculate the middle index of the current subarray
        int mid = (leftIndex + rightIndex) / 2;

        // Check if the subarray size is non-positive (no elements to sort)
        if (rightIndex - leftIndex <= 0) {
            return;
        } else if (rightIndex - leftIndex > 1) {
            // Recursively sort the left and right subarrays
            hybridSorter(arr, leftIndex, mid);
            hybridSorter(arr, mid + 1, rightIndex);
        }

        // Merge the sorted subarrays
        merge(arr, leftIndex, rightIndex);
    }

    public int sortAndCountKeyComparisons() {
        // Sort the slot array and count key comparisons
        hybridSorter(slot, 0, slot.length - 1);
        return keyComp;
    }

    public static void merge(int[] arr, int leftStart, int rightEnd) {
        // Merge function to combine two sorted subarrays

        // Check if the array is small (size less than or equal to 'S')
        if (slot.length <= S) {
            insertionSort(arr, leftStart, rightEnd); // If the array is small, use insertion sort
        }

        // Calculate the middle index of the current subarray
        int mid = (leftStart + rightEnd) / 2;

        // Initialize two pointers 'left' and 'right' for the left and right subarrays
        int left = leftStart, right = mid + 1, i, tmp;

        // Check if the subarray size is non-positive (no elements to merge)
        if (rightEnd - leftStart <= 0) {
            return;
        }

        // Iterate through the left and right subarrays and merge them
        while (left <= mid && right <= rightEnd) {
            // Compare elements at positions 'left' and 'right'
            int cmp = compare(arr[left], arr[right]);

            if (cmp > 0) { // arr[left] > arr[right]
                // Swap elements and shift elements in the right subarray to the right
                tmp = arr[right++];
                for (i = mid + 1; i > left; i--) {
                    arr[i] = arr[i - 1];
                }
                arr[left++] = tmp;
                mid++; // Increase 'mid' as we moved an element from the right to the left subarray
            } else if (cmp < 0) { // arr[left] < arr[right]
                left++; // Move the pointer in the left subarray
            } else { // arr[left] == arr[right]
                if (left == mid && right == rightEnd) {
                    break; // Both subarrays are exhausted, exit the loop
                }
                tmp = arr[right++];
                left++;
                // Shift elements in the right subarray to the right
                for (i = mid + 1; i > left; i--) {
                    arr[i] = arr[i - 1];
                }
                arr[left++] = tmp;
                mid++; // Increase 'mid' as we moved an element from the right to the left subarray
            }
        }
    }

    static int compare(int a, int b) {
        // Compare two integers and increment the key comparison count
        keyComp++;
        if (a > b)
            return 1;
        else if (a < b)
            return -1;
        else
            return 0;
    }

    static void insertionSort(int[] arr, int n, int m) {
        // Insertion sort for small subarrays
        for (int i = n + 1; i <= m; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= n && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static long[] keyCompwithInputTest() {
        // We want to test the number of key comparisons for different input sizes when
        // S is fixed.
        int count = 0;

        // Create an array to store the average comparison counts for each input size
        long[] keyCompArr = new long[10000];

        // Start testing input sizes from 10,000 to 10,000,000 in increments of 10,000.
        for (int i = 10000; i <= 10000000; i += 10000) {
            long average = 0;

            // Perform this test 5 times for each input size to get a good average result.
            for (int j = 0; j < 5; j++) {
                // Reset the key comparison count for this test.
                keyComp = 0;
                // Generate a random array of the current input size with values up to 1000.
                int[] testArr = GenerateInput.generateRandom(i, TEST_VALUE);

                // Sort the data using the 'hybridSorter' method and count key comparisons.
                hybridSorter(testArr, 0, testArr.length - 1);

                // Add the number of key comparisons made during this test to the average
                average += keyComp;
            }
            // Calculate the average number of comparisons for this input size and store it.
            keyCompArr[count++] = average / 5;

            // Print a message to show the testing progress.
            System.out.println("Testing with input size " + i +
                    " is done, and the average number of comparisons is "
                    + keyCompArr[count - 1]);
        }
        return keyCompArr;
    }

    public static long[] keyCompwithSTest() {
        // We want to test how different values of 'S' affect the sorting process.
        // The input size is fixed
        int count = 0;

        // Create an array to store the average comparison counts for each 'S' value.
        long[] keyCompArr = new long[1000];

        // Start testing 'S' values from 0 to 999.
        for (int i = 0; i < 1000; i += 1) {
            // Set the 'S' value to the current 'i'.
            S = i;

            // Perform this test 100 times to get a good average result.
            long average = 0;
            for (int j = 0; j < 100; j++) {

                // Reset the key comparison count for this test.
                keyComp = 0;

                // Generate random data to be sorted.
                int[] testArr = GenerateInput.generateRandom(1000, 1000);

                // Sort the data using the 'hybridSorter' method and count key comparisons.
                hybridSorter(testArr, 0, testArr.length - 1);

                // Add the number of key comparisons made during this test to the average.
                average += keyComp;
            }
            // Calculate the average number of comparisons for this 'S' value and store it.
            keyCompArr[count++] = average / 100;

            // Print a message to show the testing progress.
            System.out.println("Testing with S = " + i + " is done, and the average number of comparisons is "
                    + keyCompArr[count - 1]);
        }
        return keyCompArr;
    }

    public static void generateTestResults() throws IOException {

        // this is the fastest test
        long[] sTestResult = keyCompwithSTest();
        System.out.println("Key Comparison w/ S Test: " + Arrays.toString(sTestResult));
        makeCSV.CSVprinter(sTestResult, "sTestResult.csv");

        /*
         * // this test takes quite abit of time
         * long[] inputTestResult = keyCompwithInputTest();
         * System.out.println("Key Comparison w/ Input Test: " +
         * Arrays.toString(inputTestResult));
         * makeCSV.CSVprinter(inputTestResult, "inputTestResult.csv");
         */

    }

    public static void main(String[] args) throws IOException {
        int[] arr = generateRandom(100, 1000);
        System.out.println("Before Merge Sort with InsertSort:");
        System.out.println(Arrays.toString(arr) + "\n");
        newHybridSort sorter = new newHybridSort(arr);
        sorter.sortAndCountKeyComparisons();

        System.out.println("After Merge Sort with InsertSort:");
        System.out.println(Arrays.toString(newHybridSort.slot));

        System.out.println("\n Key Comparisons: " + newHybridSort.keyComp);
        generateTestResults();
    }

}
