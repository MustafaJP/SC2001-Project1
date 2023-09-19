package src;
import java.io.IOException;
import java.util.Arrays;

public class determiningS {
    static final int MAX_SIZE = 1000;
    static final int MAX_VALUE = 1000;
    static final int ITER = 50000; // Number of each test to be averaged

    public static void main(String[] args) throws IOException {
        // Measure and store the execution times for Insertion Sort and Merge Sort
        long[] insertTimeSeries = timeSort("Insertion", "insertionSort");
        long[] mergeTimeSeries = timeSort("Merge", "mergesort");

        // Print the results
        System.out.println("Insertion sort execution times: " + Arrays.toString(insertTimeSeries));
        System.out.println("Merge Sort execution times: " + Arrays.toString(mergeTimeSeries));

        // Generate CSV files with the execution times
        makeCSV.CSVprinter(insertTimeSeries, "insertTimeSeries.csv");
        makeCSV.CSVprinter(mergeTimeSeries, "mergeTimeSeries.csv");
    }

    public static long[] timeSort(String sortName, String methodName) {
        long[] timeSeries = new long[MAX_SIZE];

        // Loop through different array sizes
        for (int size = 1; size <= MAX_SIZE; size++) {
            System.out.println("Measuring sorting time for " + sortName + " with array size = " + size);
            long totalTime = 0;

            // Perform multiple iterations to average the execution time
            for (int i = 0; i < ITER; i++) {
                // Generate a random test array
                int[] testArr = GenerateInput.generateRandom(size, MAX_VALUE);
                long t0 = System.nanoTime();

                // Call the sorting method based on the methodName parameter
                if ("insertionSort".equals(methodName)) {
                    InsertionSort.insertionSort(testArr, 0, testArr.length - 1);
                } else if ("mergesort".equals(methodName)) {
                    originalMergeSort.mergesort(testArr, 0, testArr.length - 1);
                }

                // Calculate the execution time for this iteration
                totalTime += System.nanoTime() - t0;
            }

            // Calculate the average execution time and store it in the timeSeries array
            timeSeries[size - 1] = totalTime / ITER;
        }

        return timeSeries;
    }
}
