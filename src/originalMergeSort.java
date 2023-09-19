package src;
import java.util.*;

public class originalMergeSort {
	private static int[] slot;

	public static void main(String[] args) {
		initializeArray(1000, 1000); 
		System.out.println("Before Merge Sort:");
		System.out.println(Arrays.toString(slot));

		// Sort the entire array
		mergesort(slot, 0, slot.length - 1);

		System.out.println("After Merge Sort:");
		System.out.println(Arrays.toString(slot));
	}

	public static void initializeArray(int size, int max) {
        slot = generateRandom(size, max);
    }

	public static int[] generateRandom(int size, int max) {
		return new Random().ints(size, 0, max).toArray();
	}

	public static void mergesort(int[] arr, int n, int m) {
		int mid = (n + m) / 2;
		if (m - n <= 0)
			return;
		else if (m - n > 1) {
			mergesort(arr, n, mid);
			mergesort(arr, mid + 1, m);
		}
		merge(arr,n, m);
	}

	public static void merge(int[] arr,int n, int m) {
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
		if (a > b)
			return 1;
		else if (a < b)
			return -1;
		else
			return 0;
	}

}