package src;

public class compareWithOri {
    public static void main(String[] args) {
        long[] mergesortResults = new long[2];
        long[] hybridsortResults = new long[2];
        mergesortResults = newHybridSort.measureHybridSortKeyCmpAndTime();
        hybridsortResults = Mergesort.measureMergeSortKeyCmpAndTime();

        System.out.println("MergeSort Performance:");
        System.out.println("Average Key Comparisons: " + mergesortResults[0]);
        System.out.println("Average CPU Time: " + mergesortResults[1] + " ms");

        System.out.println("\nHybridSort Performance:");
        System.out.println("Average Key Comparisons: " + hybridsortResults[0]);
        System.out.println("Average CPU Time: " + hybridsortResults[1] + " ms");

    }
}
