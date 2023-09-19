package src;
public class InsertionSort {

     public static void insertionSort(int[] arr, int n, int m) {
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
}
