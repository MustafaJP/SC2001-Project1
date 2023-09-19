
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class newHybridSort {

    private static int[] slot;
    private static int S = 456; // threshold for switching to insert
    static int keyComp;
    static final int TEST_VALUE = 1000;
    static final int TEST_SIZE = 10000000;
    static final int ITER = 10; // number of each test to be averaged

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
        // Initialize the slot array if it's null
        if (slot == null) {
            slot = Arrays.copyOf(arr, arr.length);
        }
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

    public static long[] measureHybridSortKeyCmpAndTime() {
        // Initialize the result array where result[0] is key comparisons and result[1]
        // is CPU time in milliseconds
        long[] result = new long[2];

        long totalKeyCmp = 0; // Initialize total key comparisons
        long totalTime = 0; // Initialize total time

        // Loop through multiple trials
        for (int trial = 0; trial < ITER; trial++) {
            int trialNumber = trial + 1; // Calculate the current trial number

            // Print a message indicating the current trial
            System.out.println("Running Trial " + trialNumber + " (Hybrid Sort)... ");

            keyComp = 0; // Reset key comparisons for the current trial

            // Generate a random test array
            int[] testArray = GenerateInput.generateRandom(TEST_SIZE, TEST_VALUE);

            // Record the start time
            long startTime = System.currentTimeMillis();

            // Perform hybrid sort on the test array
            hybridSorter(testArray, 0, testArray.length - 1);

            // Record the end time
            long endTime = System.currentTimeMillis();

            // Calculate and accumulate the total time taken for this trial
            totalTime += endTime - startTime;

            // Accumulate the total key comparisons for this trial
            totalKeyCmp += keyComp;
        }

        // Calculate the average key comparisons and average CPU time per trial
        long averageKeyCmp = totalKeyCmp / ITER;
        long averageTime = totalTime / ITER;

        // Print the results
        System.out.println("Average Key Comparisons for Hybrid Sort: " + averageKeyCmp);
        System.out.println("Average CPU Time for Hybrid Sort: " + averageTime + " ms");

        // Store the results in the result array
        result[0] = averageKeyCmp;
        result[1] = averageTime;

        // Return the result array
        return result;
    }

    public static long[] keyCompwithInputTest() {
        // We want to test the number of key comparisons for different input sizes when
        // S is fixed.
        int count = 0;

        // Create an array to store the average comparison counts for each input size
        long[] keyCompArr = new long[12];
        int[] inputSizeList = { 1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000, 1000000, 2000000,
                5000000, 10000000
        };

        for (int i = 0; i < inputSizeList.length; i++) {
            long average = 0;

            // Perform this test 5 times for each input size to get a good average result.
            for (int j = 0; j < 5; j++) {
                // Reset the key comparison count for this test.
                keyComp = 0;
                // Generate a random array of the current input size with values up to 1000.
                int[] testArr = GenerateInput.generateRandom(inputSizeList[i], TEST_VALUE);

                // Sort the data using the 'hybridSorter' method and count key comparisons.
                hybridSorter(testArr, 0, testArr.length - 1);

                // Add the number of key comparisons made during this test to the average
                average += keyComp;
            }
            // Calculate the average number of comparisons for this input size and store it.
            keyCompArr[count++] = average / 5;

            // Print a message to show the testing progress.
            System.out.println("Testing with input size " + inputSizeList[i] +
                    " is done, and the average number of comparisons is "
                    + keyCompArr[count - 1]);
        }
        return keyCompArr;
    }

    public static long[] keyCompwithSTest() {
        // We want to test how different values of 'S' affect the sorting process.
        // The input size is fixed at 100,000
        int count = 0;

        // Create an array to store the average comparison counts for each 'S' value.
        long[] keyCompArr = new long[101];

        // Start testing 'S' values from 0 to 100.
        for (int i = 0; i < 100; i++) {
            // Set the 'S' value to the current 'i'.
            S = i;

            // Perform this test 100 times to get the average result.
            long average = 0;
            for (int j = 0; j < 100; j++) { // Keep the number of tests at 100.

                // Reset the key comparison count for this test.
                keyComp = 0;

                // Generate a random data array with 100,000 elements and values up to 10,000.
                int[] testArr = GenerateInput.generateRandom(100000, 10000);

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

        long[] sTestResult = keyCompwithSTest();
        System.out.println("Key Comparison w/ S Test: " +
                Arrays.toString(sTestResult));
        makeCSV.CSVprinter(sTestResult, "sTestResult.csv");

        // this test takes quite abit of time
        long[] inputTestResult = keyCompwithInputTest();
        System.out.println("Key Comparison w/ Input Test: " +
                Arrays.toString(inputTestResult));
        makeCSV.CSVprinter(inputTestResult, "inputTestResult.csv");

    }

    public static void main(String[] args) throws IOException {
        generateTestResults();
        // measureHybridSortKeyCmpAndTime();
    }

}





