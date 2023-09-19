package src;
public class Mergesort {
	protected static int[] slot;
	static int keyComp;
	static final int TEST_VALUE = 1000;
    static final int TEST_SIZE = 10000000;
	static final int ITER = 10; // number of each test to be averaged

	public static void mergesort(int[] arr, int n, int m) {
		int mid = (n + m) / 2;
		if (m - n <= 0)
			return;
		else if (m - n > 1) {
			mergesort(arr, n, mid);
			mergesort(arr, mid + 1, m);
		}
		merge(arr, n, m);
	}

	public static void merge(int[] arr, int n, int m) {
		int mid = (n + m) / 2;
		int a = n, b = mid + 1, i, tmp;
		if (m - n <= 0)
			return;
		while (a <= mid && b <= m) {
			int cmp = compare(arr[a], arr[b]);
			if (cmp > 0) { // slot[a] > slot[b]
				tmp = arr[b++];
				for (i = ++mid; i > a; i--)
					arr[i] = arr[i - 1];
				arr[a++] = tmp;
			} else if (cmp < 0) // slot[a] < slot[b]
				a++;
			else { // slot[a] == slot[b]
				if (a == mid && b == m)
					break;
				tmp = arr[b++];
				a++;
				for (i = ++mid; i > a; i--)
					arr[i] = arr[i - 1];
				arr[a++] = tmp;
			}
		} // end of while loop;
	} // end of merge

	public static int compare(int a, int b) {
		keyComp++;
		if (a > b)
			return 1;
		else if (a < b)
			return -1;
		else
			return 0;
	}

	public static long[] measureMergeSortKeyCmpAndTime() {
		// Initialize the result array where result[0] is key comparisons and result[1]
		// is CPU time in milliseconds
		long[] result = new long[2];

		long totalKeyCmp = 0; // Initialize total key comparisons
		long totalTime = 0; // Initialize total time

		// Loop through multiple trials
		for (int trial = 0; trial < ITER; trial++) {
			int trialNumber = trial + 1; // Calculate the current trial number

			// Print a message indicating the current trial
			System.out.println("Running Trial " + trialNumber + " (Merge Sort)... ");

			keyComp = 0; // Reset key comparisons for the current trial

			// Generate a random test array
			int[] testArray = GenerateInput.generateRandom(TEST_SIZE, TEST_VALUE);

			// Record the start time
			long startTime = System.currentTimeMillis();

			// Perform hybrid sort on the test array
			mergesort(testArray, 0, testArray.length - 1);

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
		System.out.println("Average Key Comparisons for Merge Sort: " + averageKeyCmp);
		System.out.println("Average CPU Time for Merge Sort: " + averageTime + " ms");

		// Store the results in the result array
		result[0] = averageKeyCmp;
		result[1] = averageTime;

		// Return the result array
		return result;
	}
}